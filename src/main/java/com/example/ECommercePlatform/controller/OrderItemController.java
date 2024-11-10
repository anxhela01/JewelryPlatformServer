package com.example.ECommercePlatform.controller;

import com.example.ECommercePlatform.dto.OrderCreationDto;
import com.example.ECommercePlatform.dto.OrderItemDto;
import com.example.ECommercePlatform.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

   @PostMapping("/user/create-order")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreationDto data){
       return orderItemService.createOrder(data);
   }

}

