package org.hotfilm.identityservice.Service;


import org.hotfilm.identityservice.Model.Customer;
import org.hotfilm.identityservice.ModelDTO.Response.UserResponse;

import java.util.List;

public interface CustomerService {

    List<UserResponse> findAll();

    Customer save(Customer entity);

    UserResponse findById(String string);

    void deleteById(String string);

    UserResponse updateById(String customerId, Customer customer);
}
