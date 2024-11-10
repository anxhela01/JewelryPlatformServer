package com.example.ECommercePlatform.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationDto {

    private String shippingAddress;
    private List<OrderItemDto> orderItems;
}
