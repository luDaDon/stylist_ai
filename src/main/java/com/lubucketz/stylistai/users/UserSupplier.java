package com.lubucketz.stylistai.users;

public interface UserSupplier {
    UserResponse create(CreateUserRequest request);
    UserResponse login(LoginRequest request);
}
