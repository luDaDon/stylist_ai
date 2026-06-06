package com.lubucketz.stylistai.users;

import com.lubucketz.stylistai.domain.User;

public interface UserSupplier {
    User create(CreateUserRequest request);
    User login(LoginRequest request);
}
