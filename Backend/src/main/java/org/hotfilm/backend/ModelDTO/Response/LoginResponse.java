package org.hotfilm.backend.ModelDTO.Response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    boolean authenticate;
}
