package com.lubucketz.stylistai.users;

public record CreateUserRequest(
        String username,
        String email,
        String password,
        String firstName,
        String lastName
) {}
