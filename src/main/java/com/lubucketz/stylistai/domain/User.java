package com.lubucketz.stylistai.domain;

import java.util.UUID;

public record User(
        UUID id,
        String username,
        String email,
        String firstName,
        String lastName
) {}
