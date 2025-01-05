package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class LoginResponse {
    private boolean authenticated;
}
