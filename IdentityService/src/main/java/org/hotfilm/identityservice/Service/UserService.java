package org.hotfilm.identityservice.Service;


import org.hotfilm.identityservice.Model.User;
import org.hotfilm.identityservice.ModelDTO.Request.UserRequest;
import org.hotfilm.identityservice.ModelDTO.Response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> findAll();

    User save(User entity);

    UserResponse findById(String string);

    void deleteById(String string);

    UserResponse updateById(String customerId, User customer);

    void updatePasswordByEmail(UserRequest userRequest);
}
