package com.example.ECommercePlatform.dto;

import com.example.ECommercePlatform.entities.Subcategory;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CategoryDto {
    @NotBlank
    private String name;
    @NotBlank
    private Subcategory subcategory;

}
