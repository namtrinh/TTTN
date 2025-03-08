package org.hotfilm.backend.Service;


import org.hotfilm.backend.Model.Order;
import org.hotfilm.backend.ModelDTO.Request.OrderRequest;
import org.hotfilm.backend.ModelDTO.Response.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderResponse> findAll();

    OrderResponse save(OrderRequest orderRequest);

    OrderResponse findById(String string);
}
