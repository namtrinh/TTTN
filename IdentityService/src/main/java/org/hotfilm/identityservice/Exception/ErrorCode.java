package org.hotfilm.identityservice.Exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND(400,"Not Found", HttpStatus.NOT_FOUND);


    private int code;
    private String message;
    private HttpStatus statusCode;
}
