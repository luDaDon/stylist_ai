package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.Category;
import com.lubucketz.stylistai.domain.ClothingItem;
import com.lubucketz.stylistai.security.CurrentUserService;
import com.lubucketz.stylistai.users.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClothingItemServiceTest {

    @Mock
    private ClothingItemRepository repository;

    @Mock
    private CurrentUserService currentUserService;

    @Mock
    private ClothingItemMapper mapper;

    @InjectMocks
    private ClothingItemService service;

    private UserEntity user;

    private CreateClothingItemRequest request;

    @BeforeEach
    void setUp() {

        user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setUsername("lubucketz");
        user.setEmail("lu@example.com");

        request = new CreateClothingItemRequest(
                "Black T-Shirt",
                Category.SHIRT,
                "BLACK"
        );
    }

    @Test
    void shouldCreateClothingItem() {

        UUID clothingItemId = UUID.randomUUID();

        ClothingItemEntity savedEntity =
                new ClothingItemEntity();

        savedEntity.setId(clothingItemId);
        savedEntity.setUser(user);
        savedEntity.setName("Black T-Shirt");
        savedEntity.setCategory(Category.SHIRT);
        savedEntity.setColor("BLACK");

        ClothingItem expected =
                new ClothingItem(
                        clothingItemId,
                        "Black T-Shirt",
                        Category.SHIRT,
                        "BLACK"
                );

        when(currentUserService.getCurrentUser())
                .thenReturn(user);

        when(repository.save(any(ClothingItemEntity.class)))
                .thenReturn(savedEntity);

        when(mapper.toDomain(savedEntity))
                .thenReturn(expected);

        ClothingItem result =
                service.create(request);

        assertNotNull(result);
        assertEquals(
                "Black T-Shirt",
                result.name()
        );
        assertEquals(
                Category.SHIRT,
                result.category()
        );
        assertEquals(
                "BLACK",
                result.color()
        );

        verify(currentUserService)
                .getCurrentUser();

        verify(repository)
                .save(any(ClothingItemEntity.class));

        verify(mapper)
                .toDomain(savedEntity);
    }

    @Test
    void shouldAssociateCurrentUserToClothingItem() {

        when(currentUserService.getCurrentUser())
                .thenReturn(user);

        when(repository.save(any(ClothingItemEntity.class)))
                .thenAnswer(
                        invocation -> invocation.getArgument(0)
                );

        service.create(request);

        ArgumentCaptor<ClothingItemEntity> captor =
                ArgumentCaptor.forClass(
                        ClothingItemEntity.class
                );

        verify(repository)
                .save(captor.capture());

        ClothingItemEntity saved =
                captor.getValue();

        assertEquals(
                user,
                saved.getUser()
        );

        assertEquals(
                "Black T-Shirt",
                saved.getName()
        );

        assertEquals(
                Category.SHIRT,
                saved.getCategory()
        );

        assertEquals(
                "BLACK",
                saved.getColor()
        );
    }
}