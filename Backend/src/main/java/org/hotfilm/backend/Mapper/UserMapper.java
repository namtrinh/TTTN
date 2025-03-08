package org.hotfilm.backend.Mapper;

import org.hotfilm.backend.Model.User;
import org.hotfilm.backend.ModelDTO.Request.UserRequest;
import org.hotfilm.backend.ModelDTO.Response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);

}
