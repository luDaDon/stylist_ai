package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.ClothingItem;
import com.lubucketz.stylistai.security.CurrentUserService;
import com.lubucketz.stylistai.users.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    @Override
    public Collection<ClothingItem> getByUser() {

        UserEntity user = currentUserService.getCurrentUser();

        Collection<ClothingItemEntity> clothes = repository.findByUser(user);

        return repository.findByUser(user)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public ClothingItem getById(UUID id){

        UserEntity user = currentUserService.getCurrentUser();

        return mapper.toDomain(
                getOwnedItem(id)
        );
    }

    @Override
    public void delete(UUID id) {

        UserEntity user = currentUserService.getCurrentUser();

        repository.delete(
                getOwnedItem(id)
        );
    }

    private ClothingItemEntity getOwnedItem(UUID id) {

        UserEntity user = currentUserService.getCurrentUser();

        ClothingItemEntity item = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Clothing item not found"));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return item;
    }

    @Override
    public ClothingItem update(
            UUID id,
            UpdateClothingItemRequest request
    ) {

        ClothingItemEntity item = getOwnedItem(id);

        item.setName(request.name());
        item.setCategory(request.category());
        item.setColor(request.color());

        ClothingItemEntity saved =
                repository.save(item);

        return mapper.toDomain(saved);
    }
}

