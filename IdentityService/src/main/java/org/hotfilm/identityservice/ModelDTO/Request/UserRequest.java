package org.hotfilm.identityservice.ModelDTO.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserRequest {
    private String email;

    private String password;
}
