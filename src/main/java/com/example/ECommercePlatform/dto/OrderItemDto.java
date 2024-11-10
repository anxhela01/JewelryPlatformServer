package com.example.ECommercePlatform.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Integer quantity;
    private long productId;

}

