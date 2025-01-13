package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticateResponse {

    private String token;
    boolean authenticate;
}

