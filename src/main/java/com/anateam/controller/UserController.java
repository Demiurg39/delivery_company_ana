package com.anateam.controller;

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

import com.anateam.dto.UserResponseDto;
import com.anateam.dto.UserUpdateDto;
import com.anateam.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

// TODO: Later add @PreAuthorize("hasRole('ADMIN')") for api protection
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Admin operations for managing users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    // TODO: Make validation

    private final UserService userService;

    @GetMapping()
    @Operation(summary = "Get all users", description = "Retrieves a paginated list of all registered users.")
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(Pageable pageable) {
        Page<UserResponseDto> userPage = userService.findAll(pageable);
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves detailed information about a specific user.")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponseDto>
    getUserById(@PathVariable Integer id) {
        UserResponseDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user profile", description = "Updates user information (Name, etc).")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    public ResponseEntity<UserResponseDto>
    updateUserById(@PathVariable Integer id,
                   @RequestBody UserUpdateDto userUpdateDto) {
        UserResponseDto user = userService.updateUserById(id, userUpdateDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Soft deletes or permanently removes a user from the system.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
