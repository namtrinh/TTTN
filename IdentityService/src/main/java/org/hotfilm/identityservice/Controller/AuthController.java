package org.hotfilm.identityservice.Controller;

import com.nimbusds.jose.JOSEException;
import org.hotfilm.identityservice.ModelDTO.Request.UserRequest;
import org.hotfilm.identityservice.ModelDTO.Request.VerifyCodeRequest;
import org.hotfilm.identityservice.ModelDTO.Response.ApiResponse;
import org.hotfilm.identityservice.ModelDTO.Response.AuthenticateResponse;
import org.hotfilm.identityservice.ModelDTO.Response.LoginResponse;
import org.hotfilm.identityservice.Repository.UserRepository;
import org.hotfilm.identityservice.Service.AuthService;
import org.hotfilm.identityservice.Service.EmailService;
import org.hotfilm.identityservice.Service.ResetPasswordService;
import org.hotfilm.identityservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "login")
    public ApiResponse<LoginResponse> login(@RequestBody UserRequest customer) throws JOSEException {
        return ApiResponse.<LoginResponse>builder()
                .code(200)
                .message("A verifyCode has been sent to your email!")
                .result(authService.login(customer))
                .build();
    }

    @PostMapping(value = "/verify_code")
    public ApiResponse<AuthenticateResponse> verifyAuthCode(
            @RequestBody VerifyCodeRequest verifyCodeRequest) throws JOSEException {
        AuthenticateResponse response = authService.verifyAuthCode(verifyCodeRequest);
        return ApiResponse.<AuthenticateResponse>builder()
                .code(200)
                .message("Your account has been successfully confirmed.")
                .result(response)
                .build();
    }

    @PostMapping("/reset/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestParam String email) {
      if (!userRepository.existsByEmail(email)){
            return ApiResponse.<String>builder()
                    .code(404)
                    .message("Couldn't find this email address")
                    .result("false")
                    .build();
        }
        try {
            String reset_key = UUID.randomUUID().toString();
            resetPasswordService.storeResetKey(email, reset_key);

            String resetLink = "http://localhost:4200/reset-password?email=" + email + "&reset_key=" + reset_key;
            emailService.sendCodeToMail(email, "Reset your password", "Click this url to reset password: " + resetLink);

            return ApiResponse.<String>builder()
                    .code(200)
                    .message("One url has been sent to your email")
                    .result("true")
                    .build();
        } catch (IllegalStateException e) {
            return ApiResponse.<String>builder()
                    .code(429) // Mã lỗi 429 Too Many Requests
                    .message(e.getMessage()) // Thông báo lỗi nếu vượt quá giới hạn
                    .result("false")
                    .build();
        }
    }

    @PostMapping("/reset/reset-password")
    public ApiResponse<String> resetPassword(
            @RequestParam String reset_key, @RequestBody UserRequest userRequest) {
        String cachedResetKey = resetPasswordService.getResetKey(userRequest.getEmail());

        if (cachedResetKey == null || !cachedResetKey.equals(reset_key)) {
            return ApiResponse.<String>builder()
                    .code(200)
                    .message("reset_key is invalid or expired.")
                    .result("false")
                    .build();
        }
        userService.updatePasswordByEmail(userRequest);
        resetPasswordService.removeResetKey(userRequest.getEmail());
        return ApiResponse.<String>builder()
                .code(200)
                .message("Password has been updated successfully")
                .result("true")
                .build();
    }
}
