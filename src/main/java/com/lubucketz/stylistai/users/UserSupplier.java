package com.lubucketz.stylistai.users;

public interface UserSupplier {
    User create(CreateUserRequest request);
}
