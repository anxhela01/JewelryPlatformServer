package com.example.ECommercePlatform.dto;

import com.example.ECommercePlatform.entities.GenderCategory;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {
    private int size;
    private int page;
    private List<Long> categoryIds;
    private GenderCategory genderCategory;
    private double minPrice;
    private double maxPrice;
}
