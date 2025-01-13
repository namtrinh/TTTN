package org.hotfilm.identityservice.ModelDTO.Request;

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
