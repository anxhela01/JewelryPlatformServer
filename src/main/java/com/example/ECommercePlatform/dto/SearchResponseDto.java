package com.example.ECommercePlatform.dto;
import com.example.ECommercePlatform.entities.GenderCategory;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseDto {
    private String description;
    private double price;
    private String category;
    private GenderCategory genderCategory;
    private String brand;
    private int availableQuantity;
}
