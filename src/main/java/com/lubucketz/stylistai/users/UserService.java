package com.lubucketz.stylistai.users;

import com.lubucketz.stylistai.domain.User;
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
    public User create(CreateUserRequest request) {
        UserEntity user = new UserEntity();

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
    public User login(LoginRequest request) {
        UserEntity user = repository.findByEmail(request.email())
                .orElseThrow();

        boolean matches = encoder.matches(request.password(), user.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid credentials");
        }

        return createResponse(user);
    }

    public User createResponse(UserEntity user) {
        return new User(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
