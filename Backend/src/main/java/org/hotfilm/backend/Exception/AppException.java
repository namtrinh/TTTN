package org.hotfilm.backend.Exception;

import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppException extends RuntimeException{
    private ErrorCode errorCode;
}
