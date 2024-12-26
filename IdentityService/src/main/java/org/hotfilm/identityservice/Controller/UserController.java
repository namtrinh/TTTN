package org.hotfilm.identityservice.Controller;


import org.hotfilm.identityservice.Model.User;
import org.hotfilm.identityservice.ModelDTO.Response.ApiResponse;
import org.hotfilm.identityservice.ModelDTO.Response.UserResponse;
import org.hotfilm.identityservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class UserController {

    @Autowired
    private UserService customerService;

    @GetMapping
    public ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .result(customerService.findAll())
                .build();
    }

    @PostMapping
    public ApiResponse<User> create(@RequestBody User customer) {
        return ApiResponse.<User>builder()
                .code(200)
                .result(customerService.save(customer))
                .build();
    }

    @GetMapping(value = "/{customerId}")
    public ApiResponse<UserResponse> getById(@PathVariable String customerId) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(customerService.findById(customerId))
                .build();
    }

    @DeleteMapping(value = "/{customerId}")
    public ApiResponse deleteById(@PathVariable String customerId) {
        customerService.deleteById(customerId);
        return ApiResponse.<User>builder()
                .code(200)
                .message("User has been deleted successfully !")
                .build();
    }

    @PutMapping(value = "/{customerId}")
    public ApiResponse<UserResponse> updateById(@PathVariable String customerId, @RequestBody User customer){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(customerService.updateById(customerId, customer))
                .build();
    }
}
