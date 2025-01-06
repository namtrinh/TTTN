package org.hotfilm.identityservice.Exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND,"Not Found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "Unauthorized", HttpStatus.UNAUTHORIZED),
    REQUEST_LIMIT_EXCEEDED( HttpStatus.BAD_REQUEST, "Request Limit Exceeded", HttpStatus.BAD_REQUEST),
    VERIFY_CODE_EXPIRED( HttpStatus.BAD_REQUEST, "Verified Code Exceeded", HttpStatus.BAD_REQUEST),
    EXPIRE_TOKEN( HttpStatus.BAD_REQUEST, "Expired Token", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN( HttpStatus.UNAUTHORIZED, "Invalid Token", HttpStatus.UNAUTHORIZED);

    private HttpStatus status;
    private String message;
    private HttpStatus statusCode;
}
