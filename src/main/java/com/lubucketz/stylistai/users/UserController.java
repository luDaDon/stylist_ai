package com.lubucketz.stylistai.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserSupplier supplier;

    public UserController(UserSupplier supplier){
        this.supplier = supplier;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest request) {
        UserResponse user = supplier.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
