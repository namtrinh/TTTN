package org.hotfilm.backend.ModelDTO.Response;

import lombok.*;
import org.springframework.http.HttpStatus;

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
