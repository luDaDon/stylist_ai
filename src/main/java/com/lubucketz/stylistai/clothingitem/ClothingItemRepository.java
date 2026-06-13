package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ClothingItemRepository extends JpaRepository<ClothingItemEntity, UUID> {
    Collection<ClothingItemEntity> findByUser(UserEntity user);
    Optional<ClothingItemEntity> findById(UUID id);
}
