package com.anateam.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anateam.dto.CourierProfileDto;
import com.anateam.dto.CourierStatusUpdateDto;
import com.anateam.dto.GpsCoordinatesDto;
import com.anateam.entity.User;
import com.anateam.repository.UserRepository;
import com.anateam.service.CourierService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/couriers")
@RequiredArgsConstructor
@Tag(name = "Courier Management", description = "Endpoints for managing courier statuses and locations")
@SecurityRequirement(name = "bearerAuth")
public class CourierController {
    private final CourierService courierService;
    private final UserRepository userRepository;

    @PutMapping("/me/status")
    @Operation(summary = "Update courier status", description = "Allows a courier to change their availability status.")
    @ApiResponse(responseCode = "200", description = "Status updated successfully")
    public ResponseEntity<CourierProfileDto>
    updateMyStatus(@Valid @RequestBody CourierStatusUpdateDto statusDto,
                   @AuthenticationPrincipal UserDetails userDetails) {
        Integer courierId = getAppUserFromUserDetails(userDetails).getId();
        CourierProfileDto updatedProfile =
            courierService.updateStatus(courierId, statusDto.status());

        return ResponseEntity.ok(updatedProfile);
    }

    @PutMapping("/me/location")
    @Operation(summary = "Update GPS location", description = "Updates the current GPS coordinates of the courier.")
    @ApiResponse(responseCode = "204", description = "Location updated successfully")
    public ResponseEntity<Void>
    updateMyLocation(@Valid @RequestBody GpsCoordinatesDto locationDto,
                     @AuthenticationPrincipal UserDetails userDetails) {
        Integer courierId = getAppUserFromUserDetails(userDetails).getId();
        courierService.updateLocation(courierId, locationDto);
        return ResponseEntity.noContent().build();
    }

    // --- for Admin ---

    @GetMapping
    @Operation(summary = "Get all couriers", description = "Retrieves a paginated list of all couriers (Admin use).")
    public ResponseEntity<Page<CourierProfileDto>>
    getAllCouriers(Pageable pageable) {
        Page<CourierProfileDto> couriers = courierService.findAll(pageable);
        return ResponseEntity.ok(couriers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get courier by ID", description = "Retrieves profile information of a specific courier.")
    @ApiResponse(responseCode = "200", description = "Courier found")
    @ApiResponse(responseCode = "404", description = "Courier not found")
    public ResponseEntity<CourierProfileDto>
    getCourierById(@PathVariable Integer id) {
        CourierProfileDto courier = courierService.findDtoById(id);
        return ResponseEntity.ok(courier);
    }

    private User getAppUserFromUserDetails(UserDetails userDetails) {
        String phoneNumber = userDetails.getUsername(); // Получаем номер телефона
        return userRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow( () -> new RuntimeException("Аутентифицированный пользователь не найден в БД"));
    }
}
