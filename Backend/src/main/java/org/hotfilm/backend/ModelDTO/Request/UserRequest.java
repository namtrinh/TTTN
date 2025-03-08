package org.hotfilm.backend.ModelDTO.Request;

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
