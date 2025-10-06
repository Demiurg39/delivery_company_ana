package com.anateam.controller;
import com.anateam.dto.UserResponseDto;
import com.anateam.dto.UserUpdateDto;
import com.anateam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: Later add @PreAuthorize("hasRole('ADMIN')") for api protection
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    // TODO: Make validation

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(Pageable pageable) {
        Page<UserResponseDto> userPage = userService.findAll(pageable);
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto>
    getUserById(@PathVariable Integer id) {
        UserResponseDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto>
    updateUserById(@PathVariable Integer id,
                   @RequestBody UserUpdateDto userUpdateDto) {
        UserResponseDto user = userService.updateUserById(id, userUpdateDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
