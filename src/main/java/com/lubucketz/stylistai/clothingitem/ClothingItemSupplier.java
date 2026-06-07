package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.ClothingItem;

public interface ClothingItemSupplier {
    ClothingItem create( CreateClothingItemRequest request );
}
