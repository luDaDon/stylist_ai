package com.lubucketz.stylistai.security;

public interface JwtSupplier {
    String generateToken(String email);
}
