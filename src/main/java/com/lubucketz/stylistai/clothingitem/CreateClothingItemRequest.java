package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.Category;

public record CreateClothingItemRequest (
        String name,
        Category category,
        String color
) {}
