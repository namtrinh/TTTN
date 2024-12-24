package org.hotfilm.identityservice.Model;

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
public class Customer implements Serializable {

    public static final Integer serializableId = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerId;

    private String customerName;

    private String customerAge;

    @Enumerated(EnumType.STRING)
    private ROLE roles;

    private String oauthId;

    private LocalDateTime timeCreated = LocalDateTime.now();

    public enum ROLE{
        ADMIN, USER
    }

}
