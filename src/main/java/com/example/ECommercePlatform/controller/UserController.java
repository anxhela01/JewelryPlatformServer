package com.example.ECommercePlatform.controller;

import com.example.ECommercePlatform.dto.UserLogInRequest;
import com.example.ECommercePlatform.dto.UserSignUpRequest;
import com.example.ECommercePlatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signUp")
    public ResponseEntity<?> userSignUp(@RequestBody UserSignUpRequest request) {
        return  userService.userSignUp(request);
    }


    @PostMapping("/user/logIn")
    public  ResponseEntity<?> userLogin(@RequestBody UserLogInRequest request){
        return userService.userLogIn(request);
    }




}
