package com.example.ECommercePlatform.controller;

import com.example.ECommercePlatform.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {
private final CartService cartService;
}
