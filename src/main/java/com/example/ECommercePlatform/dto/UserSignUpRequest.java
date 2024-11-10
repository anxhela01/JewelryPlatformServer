package com.example.ECommercePlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequest {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
}
