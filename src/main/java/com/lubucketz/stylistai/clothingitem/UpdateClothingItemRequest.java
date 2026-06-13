package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.Category;

public record UpdateClothingItemRequest(
        String name,
        Category category,
        String color
) {}
