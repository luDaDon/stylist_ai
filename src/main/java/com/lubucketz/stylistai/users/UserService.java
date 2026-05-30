package com.lubucketz.stylistai.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserSupplier{

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(
            UserRepository repository,
            PasswordEncoder encoder
    ){
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        User user = new User();

        user.setId(UUID.randomUUID());
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(
                encoder.encode(request.password())
        );
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

        repository.save(user);

        return createResponse(user);
    }

    @Override
    public UserResponse login(LoginRequest request) {
        User user = repository.findByEmail(request.email())
                .orElseThrow();

        boolean matches = encoder.matches(request.password(), user.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid credentials");
        }

        return createResponse(user);
    }

    public UserResponse createResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
