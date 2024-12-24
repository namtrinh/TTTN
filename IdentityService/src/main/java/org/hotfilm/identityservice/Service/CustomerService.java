package org.hotfilm.identityservice.Service;


import org.hotfilm.identityservice.Model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    Customer save(Customer entity);

    Customer findById(String string);

    void deleteById(String string);

    Customer updateById(String customerId, Customer customer);
}
