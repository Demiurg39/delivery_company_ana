package com.anateam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anateam.dto.UserResponseDto;
import com.anateam.dto.UserUpdateDto;
import com.anateam.entity.User;
import com.anateam.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
            user.getId(), user.getFullName(), user.getPhoneNumber(),
            user.getRole().toString(), user.getCreatedAt().toString());
    }

    @Override
    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toUserResponseDto);
    }

    @Override
    public UserResponseDto findById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new RuntimeException("User not found"));
        return toUserResponseDto(user);
    }

    @Override
    public UserResponseDto updateUserById(Integer id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new RuntimeException("User not found"));

        String name = userUpdateDto.fullName();
        String phone = userUpdateDto.phoneNumber();

        if (name != null)
            user.setFullName(name);
        if (phone != null)
            user.setPhoneNumber(phone);

        userRepository.save(user);

        return toUserResponseDto(user);
    }

    @Override
    public void deleteById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
