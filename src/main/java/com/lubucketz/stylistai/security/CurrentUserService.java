package com.lubucketz.stylistai.security;


import com.lubucketz.stylistai.users.UserEntity;
import com.lubucketz.stylistai.users.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserRepository repository;

    public CurrentUserService(
            UserRepository repository
    ) {
        this.repository = repository;
    }

    public UserEntity getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();

        String email = jwt.getSubject();

        return repository.findByEmail(email).orElseThrow();
    }
}
