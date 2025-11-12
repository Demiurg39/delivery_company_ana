package com.anateam.controller;
import com.anateam.dto.CourierProfileDto;
import com.anateam.dto.CourierStatusUpdateDto;
import com.anateam.dto.GpsCoordinatesDto;
import com.anateam.entity.User;
import com.anateam.repository.UserRepository;
import com.anateam.service.CourierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/couriers")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;
    private final UserRepository userRepository;

    @PutMapping("/me/status")
    // public ResponseEntity<CourierProfileDto>
    public ResponseEntity<CourierProfileDto>
    updateMyStatus(@Valid @RequestBody CourierStatusUpdateDto statusDto,
                   @AuthenticationPrincipal UserDetails userDetails) {
        Integer courierId = getAppUserFromUserDetails(userDetails).getId();
        CourierProfileDto updatedProfile =
            courierService.updateStatus(courierId, statusDto.status());
        return ResponseEntity.ok(updatedProfile);
    }

    @PutMapping("/me/location")
    public ResponseEntity<Void>
    updateMyLocation(@Valid @RequestBody GpsCoordinatesDto locationDto,
                     @AuthenticationPrincipal UserDetails userDetails) {
        Integer courierId = getAppUserFromUserDetails(userDetails).getId();
        courierService.updateLocation(courierId, locationDto);
        return ResponseEntity.noContent().build();
    }

    // --- for Admin ---

    @GetMapping
    public ResponseEntity<Page<CourierProfileDto>>
    getAllCouriers(Pageable pageable) {
        Page<CourierProfileDto> couriers = courierService.findAll(pageable);
        return ResponseEntity.ok(couriers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourierProfileDto>
    getCourierById(@PathVariable Integer id) {
        CourierProfileDto courier = courierService.findDtoById(id);
        return ResponseEntity.ok(courier);
    }

    private User getAppUserFromUserDetails(UserDetails userDetails) {
        String phoneNumber =
            userDetails.getUsername(); // Получаем номер телефона
        return userRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(
                ()
                    -> new RuntimeException(
                        "Аутентифицированный пользователь не найден в БД"));
    }
}
