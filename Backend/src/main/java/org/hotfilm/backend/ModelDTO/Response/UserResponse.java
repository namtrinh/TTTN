package org.hotfilm.backend.ModelDTO.Response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserResponse implements Serializable {
    private String username;
    private String email;
    private String phoneNumber;
    private String fullName;
}
