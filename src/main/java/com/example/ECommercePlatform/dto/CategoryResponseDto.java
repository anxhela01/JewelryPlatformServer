package com.example.ECommercePlatform.dto;

import com.example.ECommercePlatform.entities.Subcategory;

public interface CategoryResponseDto {
    long getId();
    String getName();
    Subcategory getSubcategory();
}
