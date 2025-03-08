package org.hotfilm.backend.Mapper;

import org.hotfilm.backend.Model.Order;
import org.hotfilm.backend.ModelDTO.Request.OrderRequest;
import org.hotfilm.backend.ModelDTO.Response.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequest orderRequest);

    OrderResponse toOrderResponse(Order order);
}
