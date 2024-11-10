package com.example.ECommercePlatform.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {

    private long id;
    private int quantity;
    private double price;
}
