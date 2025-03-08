package org.hotfilm.backend.ModelDTO.Request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class VerifyCodeRequest {
    private String email;

    private String authCode;
}
