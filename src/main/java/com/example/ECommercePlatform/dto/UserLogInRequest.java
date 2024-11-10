package com.example.ECommercePlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogInRequest {
    private String email;
    private String password;

}
