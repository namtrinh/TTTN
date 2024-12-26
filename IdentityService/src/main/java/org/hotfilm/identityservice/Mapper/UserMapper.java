package org.hotfilm.identityservice.Mapper;

import org.hotfilm.identityservice.Model.Customer;
import org.hotfilm.identityservice.ModelDTO.Request.UserRequest;
import org.hotfilm.identityservice.ModelDTO.Response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Customer toUser(UserRequest userRequest);

    UserResponse toUserResponse(Customer customer);

}
