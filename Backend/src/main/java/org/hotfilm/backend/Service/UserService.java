package org.hotfilm.backend.Service;


import org.hotfilm.backend.Model.User;
import org.hotfilm.backend.ModelDTO.Request.UserRequest;
import org.hotfilm.backend.ModelDTO.Response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> findAll();

    User save(User entity);

    UserResponse findById(String string);

    void deleteById(String string);

    UserResponse updateById(String customerId, User customer);

    void updatePasswordByEmail(UserRequest userRequest);
}
