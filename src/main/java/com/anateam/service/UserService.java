package com.anateam.service;

import com.anateam.dto.UserResponseDto;
import com.anateam.dto.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    public Page<UserResponseDto> findAll(Pageable pageable);

    public UserResponseDto findById(Integer id);

    public UserResponseDto updateUserById(Integer id, UserUpdateDto userUpdateDto);

    public void deleteById(Integer id);
}
