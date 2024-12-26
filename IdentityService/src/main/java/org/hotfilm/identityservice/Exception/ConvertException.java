package org.hotfilm.identityservice.Exception;

import org.hotfilm.identityservice.ModelDTO.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConvertException {
        // catch error directly
        @ExceptionHandler(value = RuntimeException.class)
        ResponseEntity<ApiResponse> getHandlingException(RuntimeException exception) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage(exception.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }
