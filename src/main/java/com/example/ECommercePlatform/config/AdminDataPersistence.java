package com.example.ECommercePlatform.config;


import com.example.ECommercePlatform.entities.Role;
import com.example.ECommercePlatform.entities.User;
import com.example.ECommercePlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminDataPersistence implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        //saveAdmin();
    }

    public void saveAdmin(){
        User admin = User.builder().name("Administrator").role(Role.ADMIN).email("admin@admin.com").password(passwordEncoder.encode("admin")).phoneNumber("r454555").build();
        userRepository.save(admin);
    }
}

