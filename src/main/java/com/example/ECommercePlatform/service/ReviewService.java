package com.example.ECommercePlatform.service;

import com.example.ECommercePlatform.dto.ReviewCreationData;
import com.example.ECommercePlatform.entities.Review;
import com.example.ECommercePlatform.entities.User;
import com.example.ECommercePlatform.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public ResponseEntity<?> createReview(ReviewCreationData data) {

        List<Review> reviews = reviewRepository.findAll();

        boolean userHasMadeReviewBefore = reviews.stream().anyMatch(review -> review.getUser().getId() == data.getUserId());

        if (userHasMadeReviewBefore) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User has already made a review.");
        }

        Review toBeSaved = Review.builder()
                .user(userService.getAuthenticatedUser())
                .comment(data.getComment())
                .rating(data.getRating())
                .build();

        reviewRepository.save(toBeSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body("Review created successfully.");
    }
}
