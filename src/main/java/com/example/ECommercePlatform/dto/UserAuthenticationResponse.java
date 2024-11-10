package com.example.ECommercePlatform.dto;

import com.example.ECommercePlatform.entities.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserAuthenticationResponse {
    private String token;
    private String message;
    private Role role;
    private LocalDateTime expiration;

}


