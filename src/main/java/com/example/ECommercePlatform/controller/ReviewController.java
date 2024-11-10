package com.example.ECommercePlatform.controller;

import com.example.ECommercePlatform.dto.ReviewCreationData;
import com.example.ECommercePlatform.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/user/create-review")
    public ResponseEntity<?> createReview (@RequestBody @Valid ReviewCreationData data){
        return reviewService.createReview(data);
    }

}
