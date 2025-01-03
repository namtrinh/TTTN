package org.hotfilm.identityservice.Controller;

import com.nimbusds.jose.JOSEException;
import org.hotfilm.identityservice.ModelDTO.Request.UserRequest;
import org.hotfilm.identityservice.ModelDTO.Response.ApiResponse;
import org.hotfilm.identityservice.ServiceImp.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "login")
    public ApiResponse<String> login(@RequestBody UserRequest customer) throws JOSEException {
        return ApiResponse.<String>builder()
                .code(200)
                .result(authService.login(customer))
                .build();
    }
}
