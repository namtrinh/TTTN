package org.hotfilm.backend.Controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.NonFinal;
import org.hotfilm.backend.Exception.AppException;
import org.hotfilm.backend.Exception.ErrorCode;
import org.hotfilm.backend.Mapper.UserMapper;
import org.hotfilm.backend.Model.User;
import org.hotfilm.backend.ModelDTO.Request.*;
import org.hotfilm.backend.ModelDTO.Response.ApiResponse;
import org.hotfilm.backend.ModelDTO.Response.AuthenticateResponse;
import org.hotfilm.backend.ModelDTO.Response.CheckTokenResponse;
import org.hotfilm.backend.ModelDTO.Response.LoginResponse;
import org.hotfilm.backend.Repository.UserRepository;
import org.hotfilm.backend.Service.AuthService;
import org.hotfilm.backend.Service.EmailService;
import org.hotfilm.backend.Service.ResetPasswordService;
import org.hotfilm.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Value("${link.reset.password}")
    private String linkResetPass;

    @NonFinal
    @Value("${jwt.refreshable-Duration}")
    protected long REFRESHABLE_DURATION;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private HashOperations<String, String, Object> hashOperations;

    AuthController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    @PostMapping(value = "login")
    public ApiResponse<LoginResponse> login(@RequestBody UserRequest customer){
        return ApiResponse.<LoginResponse>builder()
                .status(HttpStatus.OK)
                .message("A verifyCode has been sent to your email!")
                .result(authService.login(customer))
                .build();
    }

    @PostMapping(value = "register")
    public ApiResponse<String> register(@RequestBody UserRequest customer) {
        return ApiResponse.<String>builder()
                .status(HttpStatus.OK)
                .message("Your account has beent created successfully!")
                .result(authService.register(customer))
                .build();
    }

    @PostMapping(value = "/verify_code")
    public ApiResponse<String> verifyAuthCode(
            @RequestBody VerifyCodeRequest verifyCodeRequest, HttpServletResponse httpServletResponse) throws JOSEException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, ParseException {

        if (!authService.verifyAuthCode(verifyCodeRequest)){
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        User user = userRepository.findByEmail(verifyCodeRequest.getEmail()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(verifyCodeRequest.getEmail(), user.getPassword())
        );
        String token = authService.generationJwt(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.print(token);

        SignedJWT signedJWT = SignedJWT.parse(token);
        String tokenId = signedJWT.getJWTClaimsSet().getJWTID();

        String HASHKEY_JWT = "HASHKEY_JWT";

        String secretKey = "1234567890123456";
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encrypted = cipher.doFinal(tokenId.getBytes());
        String jwtId = Base64.getEncoder().encodeToString(encrypted);
        hashOperations.put(HASHKEY_JWT, tokenId, token);
        redisTemplate.expire(HASHKEY_JWT, REFRESHABLE_DURATION, TimeUnit.MILLISECONDS);
        Cookie cookie = new Cookie("jwt", jwtId);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // set false nếu chưa dùng HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        httpServletResponse.addCookie(cookie);


        return ApiResponse.<String>builder()
                .status(HttpStatus.OK)
                .message("Your account has been successfully confirmed.")
                .result("ok")
                .build();
    }

    @PostMapping("/check_token")
    public ApiResponse<Boolean> authenticateToken(@RequestBody String checkTokenRequest)
            throws ParseException, JOSEException {
        var result = authService.isValidToken(checkTokenRequest);
        return ApiResponse.<Boolean>builder()
                .status(HttpStatus.OK)
                .result(result)
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestParam String email) {
        if (!userRepository.existsByEmail(email)) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.OK)
                    .message("Couldn't find this email address")
                    .result("false")
                    .build();
        }
        try {
            String reset_key = UUID.randomUUID().toString();
            resetPasswordService.storeResetKey(email, reset_key);

            String resetLink = linkResetPass + "/reset-password?email=" + email + "&reset_key=" + reset_key;
            emailService.sendCodeToMail(email, "Reset your password", "Click this url to reset password: " + resetLink);

            return ApiResponse.<String>builder()
                    .status(HttpStatus.OK)
                    .message("One url has been sent to your email")
                    .result("true")
                    .build();
        } catch (IllegalStateException e) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.TOO_MANY_REQUESTS) // Mã lỗi 429 Too Many Requests
                    .message(e.getMessage()) // Thông báo lỗi nếu vượt quá giới hạn
                    .result("false")
                    .build();
        }
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(
            @RequestParam String reset_key, @RequestBody UserRequest userRequest) {
        String cachedResetKey = resetPasswordService.getResetKey(userRequest.getEmail());

        if (cachedResetKey == null || !cachedResetKey.equals(reset_key)) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_GATEWAY)
                    .message("reset_key is invalid or expired.")
                    .result("false")
                    .build();
        }
        userService.updatePasswordByEmail(userRequest);
        resetPasswordService.removeResetKey(userRequest.getEmail());
        return ApiResponse.<String>builder()
                .status(HttpStatus.OK)
                .message("Password has been updated successfully")
                .result("true")
                .build();
    }
}
