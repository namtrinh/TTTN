package org.hotfilm.identityservice.Exception;

import com.cloudinary.Api;
import org.apache.coyote.Response;
import org.hotfilm.identityservice.ModelDTO.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerResponse {
    // catch error directly

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> getRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> getAppException(AppException exception) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(exception.getErrorCode().getStatus());
        apiResponse.setMessage(exception.getErrorCode().getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(JwtException.class)
    ResponseEntity<ApiResponse> getJwtException( JwtException exception ){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    ResponseEntity<ApiResponse> getAuthenticationServiceException( AuthenticationServiceException exception ){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
