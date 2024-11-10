package com.example.ECommercePlatform.service;

import com.example.ECommercePlatform.dto.CategoryDto;
import com.example.ECommercePlatform.entities.Category;
import com.example.ECommercePlatform.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> createCategory(CategoryDto data) {


        Category category = Category.builder()
                .name(data.getName())
                .subcategory(data.getSubcategory())
                .build();


        categoryRepository.save(category);
        return ResponseEntity.ok().body("Category created");
    }


}
