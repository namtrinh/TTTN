package org.hotfilm.identityservice.ModelDTO.Request;

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
