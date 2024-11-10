package com.example.ECommercePlatform.entities;

import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int rating;
    private String comment;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;




}
