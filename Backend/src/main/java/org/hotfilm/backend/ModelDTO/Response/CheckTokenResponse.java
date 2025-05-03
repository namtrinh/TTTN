package org.hotfilm.backend.ModelDTO.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckTokenResponse {
    private boolean valid;
}
