package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.Category;
import com.lubucketz.stylistai.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="clothing_items")
public class ClothingItemEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String color;
}
