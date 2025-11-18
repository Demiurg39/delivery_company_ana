package com.anateam.service;

import java.util.List;

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
    public UserResponseDto register(UserRegistrationDto registrationDto) {
        userRepository.findByPhoneNumber(registrationDto.phoneNumber())
            .ifPresent(user -> {
                throw new IllegalStateException(
                    "User with this phone number already exists.");
            });

        String hashedPassword =
            passwordEncoder.encode(registrationDto.password());

        User newUser = new User();
        newUser.setFullName(registrationDto.fullName());
        newUser.setPhoneNumber(registrationDto.phoneNumber());
        newUser.setPasswordHash(hashedPassword);
        newUser.setRole(UserRole.CUSTOMER);

        User savedUser = userRepository.save(newUser);

        return toUserResponseDto(savedUser);
    }

    public AuthResponseDto login(LoginDto loginDto) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.phoneNumber(),
                                                    loginDto.password()));

        User appUser =
            userRepository.findByPhoneNumber(loginDto.phoneNumber())
                .orElseThrow(()
                                 -> new IllegalStateException(
                                     "Error while token generation"));

        UserDetails userDetails =
            new org.springframework.security.core.userdetails.User(
                appUser.getPhoneNumber(), appUser.getPasswordHash(),
                List.of(new SimpleGrantedAuthority(appUser.getRole().name())));

        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthResponseDto(jwtToken);
    }

    private UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(user.getId(), user.getFullName(),
                                   user.getPhoneNumber(), user.getRole().name(),
                                   user.getCreatedAt().toString());
    }
}
