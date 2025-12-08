package com.anateam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.anateam.entity.User;
import com.anateam.entity.UserRole;
import com.anateam.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${default.admin.phonenumber}")
    private String adminDefaultPhone;

    @Value("${default.admin.password}")
    private String adminDefaultPass;


    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            // Check if any admin user exists
            boolean adminExists = userRepository.findAll().stream()
                    .anyMatch(user -> user.getRole() == UserRole.ADMIN);

            if (!adminExists) {
                // Create default admin account
                User admin = new User();
                admin.setFullName("Admin User");
                admin.setPhoneNumber(adminDefaultPhone);
                admin.setPasswordHash(passwordEncoder.encode(adminDefaultPass));
                admin.setRole(UserRole.ADMIN);
                admin.setIsVerified(true);

                userRepository.save(admin);

                log.info("✅ Default admin account created:");
                log.info("   Phone: " + adminDefaultPhone);
                log.info("   Password: "+ adminDefaultPass);
                log.info("   IMPORTANT: Change this password in production!");
            } else {
                log.info("Admin account already exists, skipping seeding");
            }

            boolean testUserExists = userRepository.findAll().stream().anyMatch(user -> user.getFullName() == "TestUser");

            // TODO: create test user
            if (!testUserExists) {}
        };
    }
}
