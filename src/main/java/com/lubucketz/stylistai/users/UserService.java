package com.lubucketz.stylistai.users;

import com.lubucketz.stylistai.config.PasswordConfig;
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
    public User create(CreateUserRequest request){
        User user = new User();

        user.setId(UUID.randomUUID());
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(
                encoder.encode(request.password())
        );
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

        return repository.save(user);
    }
}
