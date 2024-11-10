package com.example.ECommercePlatform.dto;


import com.example.ECommercePlatform.entities.GenderCategory;


import java.util.List;


public interface ProductResponseDto {
    CategoryResponseDto getCategory();
    GenderCategory getGenderCategory();
    String getDescription();
    String getBrand();
    double getPrice();
    int getAvailableQuantity();
    List<ProductImageResponseDto> getProductImages();
}

