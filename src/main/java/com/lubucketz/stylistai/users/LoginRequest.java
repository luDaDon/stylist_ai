package com.lubucketz.stylistai.users;

public record LoginRequest (
    String email,
    String password
) {}
