package org.hotfilm.backend.ModelDTO.Request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckTokenRequest {

    private String token;
}
