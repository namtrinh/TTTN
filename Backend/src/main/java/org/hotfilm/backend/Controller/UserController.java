package org.hotfilm.backend.Controller;


import org.hotfilm.backend.Model.User;
import org.hotfilm.backend.ModelDTO.Response.ApiResponse;
import org.hotfilm.backend.ModelDTO.Response.UserResponse;
import org.hotfilm.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService customerService;

    @GetMapping
    public ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .status(HttpStatus.OK)
                .result(customerService.findAll())
                .build();
    }

    @PostMapping
    public ApiResponse<User> create(@RequestBody User customer) {
        return ApiResponse.<User>builder()
                .status(HttpStatus.OK)
                .result(customerService.save(customer))
                .build();
    }

    @GetMapping(value = "/{customerId}")
    public ApiResponse<UserResponse> getById(@PathVariable String customerId) {
        return ApiResponse.<UserResponse>builder()
                .status(HttpStatus.OK)
                .result(customerService.findById(customerId))
                .build();
    }

    @DeleteMapping(value = "/{customerId}")
    public ApiResponse deleteById(@PathVariable String customerId) {
        customerService.deleteById(customerId);
        return ApiResponse.<User>builder()
                .status(HttpStatus.OK)
                .message("User has been deleted successfully !")
                .build();
    }

    @PutMapping(value = "/{customerId}")
    public ApiResponse<UserResponse> updateById(@PathVariable String customerId, @RequestBody User customer){
        return ApiResponse.<UserResponse>builder()
                .status(HttpStatus.OK)
                .result(customerService.updateById(customerId, customer))
                .build();
    }
}
