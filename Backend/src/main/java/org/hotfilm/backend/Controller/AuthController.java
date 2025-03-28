package org.hotfilm.backend.Controller;

import com.nimbusds.jose.JOSEException;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.UUID;

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



    @PostMapping(value = "login")
    public ApiResponse<LoginResponse> login(@RequestBody UserRequest customer)  {
        return ApiResponse.<LoginResponse>builder()
                .status(HttpStatus.OK)
                .message("A verifyCode has been sent to your email!")
                .result(authService.login(customer))
                .build();
    }

    @PostMapping(value = "register")
    public ApiResponse<String> register(@RequestBody UserRequest customer){
        return ApiResponse.<String>builder()
                .status(HttpStatus.OK)
                .message("Your account has beent created successfully!")
                .result(authService.register(customer))
                .build();
    }

    @PostMapping(value = "/verify_code")
    public ApiResponse<AuthenticateResponse> verifyAuthCode(
            @RequestBody VerifyCodeRequest verifyCodeRequest) throws JOSEException {
        AuthenticateResponse response = authService.verifyAuthCode(verifyCodeRequest);
        return ApiResponse.<AuthenticateResponse>builder()
                .status(HttpStatus.OK)
                .message("Your account has been successfully confirmed.")
                .result(response)
                .build();
    }

    @PostMapping("/check_token")
    public ApiResponse<CheckTokenResponse> authenticateToken(@RequestBody CheckTokenRequest checkTokenRequest)
            throws ParseException, JOSEException {
        var result = authService.checkToken(checkTokenRequest);
        return ApiResponse.<CheckTokenResponse>builder()
                .status(HttpStatus.OK)
                .result(result)
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticateResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest)
            throws ParseException, JOSEException {
        var result = authService.refreshToken(refreshTokenRequest);
        return ApiResponse.<AuthenticateResponse>builder()
                .status(HttpStatus.OK)
                .result(result)
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestParam String email) {
      if (!userRepository.existsByEmail(email)){
            return ApiResponse.<String>builder()
                    .status(HttpStatus.OK)
                    .message("Couldn't find this email address")
                    .result("false")
                    .build();
        }
        try {
            String reset_key = UUID.randomUUID().toString();
            resetPasswordService.storeResetKey(email, reset_key);

            String resetLink =  linkResetPass + "/reset-password?email=" + email + "&reset_key=" + reset_key;
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

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException {
        authService.logout(logoutRequest);
        return ApiResponse.<Void>builder().build();
    }
}
