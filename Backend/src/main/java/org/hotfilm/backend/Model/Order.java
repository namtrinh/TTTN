package org.hotfilm.backend.Model;

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

    public Order(String number, String mail, String dddd, String s, int i) {
    }

    private enum OrderStatus{
    SUCCESS, CANCELLED
}

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", orderDate=" + orderDate +
                ", orderStatus=" + orderStatus +
                ", totalPrice=" + totalPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
