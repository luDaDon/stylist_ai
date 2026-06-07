package com.lubucketz.stylistai.users;

import com.lubucketz.stylistai.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(
            UserEntity entity
    ) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName()
        );
    }

}
