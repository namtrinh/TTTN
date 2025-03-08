package org.hotfilm.backend.Controller;

import org.hotfilm.backend.ModelDTO.Request.OrderRequest;
import org.hotfilm.backend.ModelDTO.Response.ApiResponse;
import org.hotfilm.backend.ModelDTO.Response.OrderResponse;
import org.hotfilm.backend.Service.OrderService;
import org.hotfilm.backend.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ApiResponse<List<OrderResponse>> getAll() {
        return ApiResponse.<List<OrderResponse>>builder()
                .status(HttpStatus.OK)
                .result(orderService.findAll())
                .build();
    }

    @PostMapping
    public ApiResponse<OrderResponse> createTicket(@RequestBody OrderRequest orderRequest) {
        return ApiResponse.<OrderResponse>builder()
                .status(HttpStatus.OK)
                .result(orderService.save(orderRequest))
                .build();
    }


}
