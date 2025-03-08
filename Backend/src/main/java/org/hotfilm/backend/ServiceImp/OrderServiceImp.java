package org.hotfilm.backend.ServiceImp;

import org.hotfilm.backend.Mapper.OrderMapper;
import org.hotfilm.backend.Model.Order;
import org.hotfilm.backend.ModelDTO.Request.OrderRequest;
import org.hotfilm.backend.ModelDTO.Response.OrderResponse;
import org.hotfilm.backend.Repository.OrderRepository;
import org.hotfilm.backend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<OrderResponse> findAll() {
        List<Order> order = orderRepository.findAll();
        return order.stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse save(OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);
        System.out.println(orderRequest.getOrderDate());
        System.out.println("hehehe");
        String dateString = "2025-03-03 15:05:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

        System.out.println(dateTime);
     order.setOrderDate(dateTime);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse findById(String string) {
        Order order = orderRepository.findById(string).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toOrderResponse(order);
    }
}

