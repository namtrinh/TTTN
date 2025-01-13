package org.hotfilm.identityservice.Exception;

import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppException extends RuntimeException{
    private ErrorCode errorCode;
}
