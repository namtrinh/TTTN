package org.hotfilm.identityservice.ModelDTO.Response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hotfilm.identityservice.Model.Customer;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserResponse {
    private String customerId;

    private String customerName;

    private String customerAge;

    private String oauthId;

    private LocalDateTime timeCreated = LocalDateTime.now();
}
