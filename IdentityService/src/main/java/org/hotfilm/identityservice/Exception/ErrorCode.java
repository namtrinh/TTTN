package org.hotfilm.identityservice.Exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ErrorCode {

    LOGIN_ACCOUNT(HttpStatus.BAD_REQUEST, "Email or Password is incorrect", HttpStatus.BAD_REQUEST),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "Unauthorized", HttpStatus.UNAUTHORIZED),
    REQUEST_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "Request Limit Exceeded", HttpStatus.BAD_REQUEST),
    VERIFY_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "Verified Code has been expired or incorrect", HttpStatus.BAD_REQUEST),
    EXPIRE_TOKEN(HttpStatus.BAD_REQUEST, "Expired Token", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid Token", HttpStatus.UNAUTHORIZED),
    ACCOUNT_EXIST(HttpStatus.BAD_REQUEST, "Account Exist", HttpStatus.BAD_REQUEST),
    NAME_NOT_NULL(HttpStatus.BAD_REQUEST, "Please enter movie name you want to search", HttpStatus.BAD_REQUEST);

    private HttpStatus status;
    private String message;
    private HttpStatus statusCode;
}
