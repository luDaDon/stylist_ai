package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.ClothingItem;
import org.springframework.stereotype.Component;

@Component
public class ClothingItemMapper {

    public ClothingItem toDomain(
            ClothingItemEntity entity
    ) {
        return new ClothingItem(
                entity.getId(),
                entity.getName(),
                entity.getCategory(),
                entity.getColor()
        );
    }
}
