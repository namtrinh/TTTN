package org.hotfilm.identityservice.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    private String name;
    private String phone;
    private String email;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private int totalPrice;
    private String paymentMethod;

private enum OrderStatus{
    SUCCESS, CANCELLED
}

}
