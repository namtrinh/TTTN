package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;

import java.util.Set;
@Data
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T result;

}
