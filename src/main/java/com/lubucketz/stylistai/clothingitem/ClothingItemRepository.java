package com.lubucketz.stylistai.clothingitem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClothingItemRepository extends JpaRepository<ClothingItemEntity, UUID> {
}
