package org.hotfilm.backend.Mapper;

import org.hotfilm.backend.Model.Order;
import org.hotfilm.backend.ModelDTO.Request.OrderRequest;
import org.hotfilm.backend.ModelDTO.Response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderDate", ignore = true)
    Order toOrder(OrderRequest orderRequest);


    OrderResponse toOrderResponse(Order order);
}
