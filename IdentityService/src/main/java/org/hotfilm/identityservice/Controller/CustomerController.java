package org.hotfilm.identityservice.Controller;


import org.hotfilm.identityservice.Model.Customer;
import org.hotfilm.identityservice.ModelDTO.Response.ApiResponse;
import org.hotfilm.identityservice.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ApiResponse<List<Customer>> getAll() {
        return ApiResponse.<List<Customer>>builder()
                .code(200)
                .result(customerService.findAll())
                .build();
    }

    @PostMapping
    public ApiResponse<Customer> create(@RequestBody Customer customer) {
        return ApiResponse.<Customer>builder()
                .code(200)
                .result(customerService.save(customer))
                .build();
    }

    @GetMapping(value = "/{customerId}")
    public ApiResponse<Customer> getById(@PathVariable String customerId) {
        return ApiResponse.<Customer>builder()
                .code(200)
                .result(customerService.findById(customerId))
                .build();
    }

    @DeleteMapping(value = "/{customerId}")
    public ApiResponse deleteById(@PathVariable String customerId) {
        customerService.deleteById(customerId);
        return ApiResponse.<Customer>builder()
                .code(200)
                .message("Customer has been deleted successfully !")
                .build();
    }

    @PutMapping(value = "/{customerId}")
    public ApiResponse<Customer> updateById(@PathVariable String customerId, @RequestBody Customer customer){
        return ApiResponse.<Customer>builder()
                .code(200)
                .result(customerService.updateById(customerId, customer))
                .build();
    }
}
