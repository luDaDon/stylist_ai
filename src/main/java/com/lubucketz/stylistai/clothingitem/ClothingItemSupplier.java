package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.ClothingItem;

import java.util.Collection;
import java.util.UUID;

public interface ClothingItemSupplier {
    ClothingItem create( CreateClothingItemRequest request );
    Collection<ClothingItem> getByUser();
    ClothingItem getById(UUID id);
    void delete(UUID id);
    ClothingItem update(
            UUID id,
            UpdateClothingItemRequest request
    );
}
