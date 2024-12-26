package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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
