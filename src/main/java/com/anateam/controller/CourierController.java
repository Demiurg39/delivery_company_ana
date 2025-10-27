package com.anateam.controller;
import com.anateam.dto.CourierProfileDto;
import com.anateam.dto.CourierStatusUpdateDto;
import com.anateam.dto.GpsCoordinatesDto;
import com.anateam.service.CourierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;

    @PutMapping("/me/status")
    // public ResponseEntity<CourierProfileDto>
    public ResponseEntity<Void>
    updateMyStatus(@Valid @RequestBody CourierStatusUpdateDto statusDto) {
        // TODO: Получить ID аутентифицированного курьера
        // Integer courierId = ...;
        // CourierProfileDto updatedProfile =
        // courierService.updateStatus(courierId, statusDto.status()); return
        // ResponseEntity.ok(updatedProfile);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/location")
    public ResponseEntity<Void>
    updateMyLocation(@Valid @RequestBody GpsCoordinatesDto locationDto) {
        // TODO: Получить ID аутентифицированного курьера
        // Integer courierId = ...;
        // courierService.updateLocation(courierId, locationDto);
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
}
