package com.lubucketz.stylistai.users;

import com.lubucketz.stylistai.security.JwtSupplier;
import com.lubucketz.stylistai.domain.User;
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
    private final JwtSupplier jwtSupplier;

    public UserController(
            UserSupplier supplier,
            JwtSupplier jwtSupplier
            ){
        this.supplier = supplier;
        this.jwtSupplier = jwtSupplier;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody CreateUserRequest request) {
        User user = supplier.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        User user = supplier.login(request);
        String token = jwtSupplier.generateToken(
                user.email()
        );
        return ResponseEntity.ok(token);
    }
}
