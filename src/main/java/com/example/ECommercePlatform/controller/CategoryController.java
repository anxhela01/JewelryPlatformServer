package com.example.ECommercePlatform.controller;

import com.example.ECommercePlatform.dto.CategoryDto;
import com.example.ECommercePlatform.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping("/admin/create-category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto data) {
        return categoryService.createCategory(data);
    }
}

