package com.anateam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anateam.dto.AuthResponseDto;
import com.anateam.dto.LoginDto;
import com.anateam.dto.UserRegistrationDto;
import com.anateam.dto.UserResponseDto;
import com.anateam.entity.User;
import com.anateam.entity.UserRole;
import com.anateam.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationDto registrationDto, UserRole role) {
        // Check if user already exists
        Optional<User> existingUser = userRepository.findByPhoneNumber(registrationDto.phoneNumber());

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // If user is verified, throw error
            if (user.getIsVerified()) {
                throw new IllegalStateException("User with this phone number already exists.");
            }

            // If user is not verified, delete the old registration and allow new one
            userRepository.delete(user);
        }

        String hashedPassword = passwordEncoder.encode(registrationDto.password());

        User newUser = new User();
        newUser.setFullName(registrationDto.fullName());
        newUser.setPhoneNumber(registrationDto.phoneNumber());
        newUser.setPasswordHash(hashedPassword);
        newUser.setRole(role);
        newUser.setIsVerified(false);

        User savedUser = userRepository.save(newUser);

        return toUserResponseDto(savedUser);
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.phoneNumber(),
                        loginDto.password()));

        User appUser = userRepository.findByPhoneNumber(loginDto.phoneNumber())
                .orElseThrow(() -> new IllegalStateException(
                        "Error while token generation"));

        if (!appUser.getIsVerified()) {
            throw new IllegalStateException("Account is not verified. Please verify your phone number.");
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                appUser.getPhoneNumber(), appUser.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + appUser.getRole().name())));

        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthResponseDto(jwtToken);
    }

    private UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(user.getId(), user.getFullName(),
                user.getPhoneNumber(), user.getRole().name(),
                user.getCreatedAt().toString());
    }

    @Override
    @Transactional
    public void sendVerificationCode(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // Generate 4-digit verification code using SecureRandom
        java.security.SecureRandom random = new java.security.SecureRandom();
        String code = String.format("%04d", random.nextInt(10000));

        // Set expiry to 5 minutes from now
        user.setVerificationCode(code);
        user.setVerificationCodeExpiry(java.time.OffsetDateTime.now().plusMinutes(5));

        userRepository.save(user);

        // Send SMS (implementation in SmsService)
        // Note: This will be called but won't actually send unless credentials are
        // configured
        // smsService.sendVerificationCode(phoneNumber, code);
    }

    @Override
    @Transactional
    public AuthResponseDto verifyCode(String phoneNumber, String code) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // Check if verification code exists
        if (user.getVerificationCode() == null) {
            throw new IllegalStateException("No verification code found. Please request a new code.");
        }

        // Check if code has expired
        if (user.getVerificationCodeExpiry().isBefore(java.time.OffsetDateTime.now())) {
            throw new IllegalStateException("Verification code has expired. Please request a new code.");
        }

        // Verify the code
        if (!user.getVerificationCode().equals(code)) {
            throw new IllegalStateException("Invalid verification code.");
        }

        // Mark user as verified
        user.setIsVerified(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiry(null);
        userRepository.save(user);

        // Generate JWT token
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(), user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));

        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthResponseDto(jwtToken);
    }
}
