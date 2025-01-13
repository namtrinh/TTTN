package org.hotfilm.identityservice.Exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND(400,"Not Found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),
    REQUEST_LIMIT_EXCEEDED(400, "Request Limit Exceeded", HttpStatus.BAD_REQUEST),
    VERIFY_CODE_EXPIRED(400, "Verified Code Exceeded", HttpStatus.BAD_REQUEST),
    EXPIRE_TOKEN(400, "Expired Token", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(400, "Invalid Token", HttpStatus.UNAUTHORIZED);

    private int code;
    private String message;
    private HttpStatus statusCode;
}
