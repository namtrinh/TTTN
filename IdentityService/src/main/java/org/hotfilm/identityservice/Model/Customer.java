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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(String customerAge) {
        this.customerAge = customerAge;
    }

    public ROLE getRoles() {
        return roles;
    }

    public void setRoles(ROLE roles) {
        this.roles = roles;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
