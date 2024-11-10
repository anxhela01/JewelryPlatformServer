package com.example.ECommercePlatform.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private int quantity;
    private long productId;
    private long cartId;

}
