package com.example.ECommercePlatform.service;

import com.example.ECommercePlatform.config.JwtService;
import com.example.ECommercePlatform.dto.UserAuthenticationResponse;
import com.example.ECommercePlatform.dto.UserLogInRequest;
import com.example.ECommercePlatform.dto.UserSignUpRequest;
import com.example.ECommercePlatform.entities.Cart;
import com.example.ECommercePlatform.entities.Role;
import com.example.ECommercePlatform.entities.User;
import com.example.ECommercePlatform.repository.CartRepository;
import com.example.ECommercePlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CartRepository cartRepository;

    public ResponseEntity<?> userSignUp(UserSignUpRequest request) {
        List<User> existingUsers = userRepository.findAll();

        if (!existingUsers.isEmpty()) {
            for (User user : existingUsers) {
                if (Objects.equals(user.getEmail(), request.getEmail()) || Objects.equals(user.getPhoneNumber(), request.getPhoneNumber())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Email or phone number already in use.");
                }
            }
        }

        Cart cart =new Cart(0);
        Cart savedCart = cartRepository.save(cart);

        User user = User.builder()
                .role(Role.USER)
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .cart(savedCart)
                .build();


        userRepository.save(user);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword())
            );

            String jwtToken = jwtService.generateToken(user);


            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(UserAuthenticationResponse
                            .builder()
                            .token(jwtToken)
                            .message("Registered successfully")
                            .role(user.getRole())
                            .expiration(jwtService.getExpiration(jwtToken))
                            .build()
                    );

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Bad Credentials");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Problem: " + e.getMessage());
        }
    }


    public ResponseEntity<?> userLogIn(UserLogInRequest request) {

        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account me kete email nuk ekziston");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword())
            );

            String jwtToken = jwtService.generateToken(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(UserAuthenticationResponse
                            .builder()
                            .token(jwtToken)
                            .message("Logged In successfully")
                            .role(user.getRole())
                            .expiration(jwtService.getExpiration(jwtToken))
                            .build()
                    );
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Bad Credentials");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Problem: " + e.getMessage());
        }

    }


    public User getAuthenticatedUser() {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authenticatedUserEmail);
    }


}

