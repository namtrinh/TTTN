package org.hotfilm.backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "customer")
public class User implements Serializable {

    public static final Integer serializableId = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String fullName;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Role {
        USER, ADMIN
    }

    private String verificationCode;

    private LocalDateTime verificationCodeExpiry;

    private Integer requestCount = 0;

    private LocalDateTime lastRequestTime;

    private Boolean activated;


}
