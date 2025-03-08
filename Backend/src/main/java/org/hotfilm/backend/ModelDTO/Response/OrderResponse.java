package org.hotfilm.backend.ModelDTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hotfilm.backend.Model.Order;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderResponse {

    private String orderId;
    private String paymentId;
    private String name;
    private String phone;
    private String email;
    private LocalDateTime orderDate;
    private Order.OrderStatus orderStatus;
    private int totalPrice;
    private String paymentMethod;
}
