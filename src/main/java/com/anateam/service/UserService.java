package com.anateam.service;

import com.anateam.dto.UserRegistrationDto;
import com.anateam.dto.UserResponseDto;
import com.anateam.dto.UserUpdateDto;
import org.springframework.data.domain.Page;

public interface UserService {

    public Page<UserResponseDto> findAll(Page pageable);

    public UserResponseDto findById(Integer id);

    public UserResponseDto updateUserById(Integer id,
                                          UserUpdateDto userUpdateDto);

    public void deleteById(Integer id);

    public UserResponseDto register(UserRegistrationDto registrationDto);
}
