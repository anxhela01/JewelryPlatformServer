package com.example.ECommercePlatform.controller;

import com.example.ECommercePlatform.dto.CartItemDto;
import com.example.ECommercePlatform.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping("/user/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody CartItemDto data) {
        return cartItemService.addToCart(data);
    }

    @DeleteMapping("/user/remove-from-cart")
    public ResponseEntity<?> removeFromCart(@RequestParam("id") Long cartItemId) {
        return cartItemService.removeFromCart(cartItemId);
    }
}
