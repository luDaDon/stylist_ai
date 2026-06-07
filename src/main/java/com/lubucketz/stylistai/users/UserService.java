package com.lubucketz.stylistai.users;

import com.lubucketz.stylistai.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserSupplier{

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    public UserService(
            UserRepository repository,
            PasswordEncoder encoder,
            UserMapper mapper
    ){
        this.repository = repository;
        this.encoder = encoder;
        this.mapper = mapper;
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

        return mapper.toDomain(user);
    }

    @Override
    public User login(LoginRequest request) {
        UserEntity user = repository.findByEmail(request.email())
                .orElseThrow();

        boolean matches = encoder.matches(request.password(), user.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid credentials");
        }

        return mapper.toDomain(user);
    }
}
