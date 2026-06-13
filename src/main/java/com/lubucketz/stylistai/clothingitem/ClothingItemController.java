package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.ClothingItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/clothing-items")
public class ClothingItemController {

    private final ClothingItemSupplier supplier;

    public ClothingItemController(ClothingItemSupplier supplier){
        this.supplier = supplier;
    }

    @PostMapping
    public ResponseEntity<ClothingItem> create(
            @RequestBody CreateClothingItemRequest request
    ) {
        ClothingItem clothingItem = supplier.create(
                request
        );

        return ResponseEntity.ok(clothingItem);
    }
}
