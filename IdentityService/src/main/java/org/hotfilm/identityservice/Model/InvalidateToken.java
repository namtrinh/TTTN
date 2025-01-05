package org.hotfilm.identityservice.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "invalidaToken")
public class InvalidateToken {
    @Id
    String id;

    Date expiryTime;
}
