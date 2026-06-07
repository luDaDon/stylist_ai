package com.lubucketz.stylistai.domain;

import java.util.UUID;

public record ClothingItem (
    UUID id,
    String name,
    Category category,
    String color
) {}
