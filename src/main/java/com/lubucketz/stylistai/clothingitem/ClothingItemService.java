package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.ClothingItem;
import com.lubucketz.stylistai.security.CurrentUserService;
import com.lubucketz.stylistai.users.UserEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClothingItemService implements ClothingItemSupplier{

    private final ClothingItemRepository repository;
    private final ClothingItemMapper mapper;
    private final CurrentUserService currentUserService;

    public ClothingItemService(
            ClothingItemRepository repository,
            ClothingItemMapper mapper,
            CurrentUserService currentUserService
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserService = currentUserService;
    }

    @Override
    public ClothingItem create (
            CreateClothingItemRequest request
    ) {

        UserEntity user = currentUserService.getCurrentUser();

        ClothingItemEntity entity = new ClothingItemEntity();

        entity.setId(UUID.randomUUID());
        entity.setUser(user);
        entity.setName(request.name());
        entity.setCategory(request.category());
        entity.setColor(request.color());

        ClothingItemEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }
}
