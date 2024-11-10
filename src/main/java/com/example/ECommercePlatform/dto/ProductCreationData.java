package com.example.ECommercePlatform.dto;


import com.example.ECommercePlatform.entities.Category;
import com.example.ECommercePlatform.entities.GenderCategory;
import com.example.ECommercePlatform.entities.ProductImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreationData {

    private Long categoryId;
    private GenderCategory genderCategory;
    private String description;
    private String brand;
    private double price;
    private int availableQuantity;
    private List<MultipartFile> productImages;

}

