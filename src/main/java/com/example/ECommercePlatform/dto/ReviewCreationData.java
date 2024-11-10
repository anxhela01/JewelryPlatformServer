package com.example.ECommercePlatform.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreationData {

    @Min(0)
    @Max(5)
    private int rating;
    @NotBlank
    private String comment;
    @Positive
    @NotNull
    private long userId;

}
