package com.lubucketz.stylistai.clothingitem;

import com.lubucketz.stylistai.domain.ClothingItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<Collection<ClothingItem>> getAllByUser() {
        return ResponseEntity.ok(
                supplier.getByUser()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClothingItem> getByUser(@PathVariable UUID id) {
        return ResponseEntity.ok(
                supplier.getById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {

        supplier.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClothingItem> update(
            @PathVariable UUID id,
            @RequestBody UpdateClothingItemRequest request
    ) {

        return ResponseEntity.ok(
                supplier.update(id, request)
        );
    }
}
