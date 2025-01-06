package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Set;
@Data
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private HttpStatus status;
    private String message;
    private T result;

}
