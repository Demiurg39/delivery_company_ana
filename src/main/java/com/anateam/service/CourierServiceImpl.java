package com.anateam.service.impl;

import com.anateam.dto.CourierProfileDto;
import com.anateam.dto.GpsCoordinatesDto;
import com.anateam.entity.Courier;
import com.anateam.repository.CourierRepository;
import com.anateam.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    @Override
    public Page<CourierProfileDto> findAll(Pageable pageable) {
        return courierRepository.findAll(pageable)
                .map(CourierProfileDto::new)
    }

    @Override
    public CourierProfileDto findDtoById(Integer courierId) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new RuntimeException("Courier not found"));
        return new CourierProfileDto(courier);

    }
    @Override
    public CourierProfileDto updateStatus(Integer courierId, String status) {
        Courier courier = courierRepository.findbyId(courierId)
                .orElseThrow(() -> new RuntimeException("Courier not found"));

        courier.setStatus(status);
        courierRepository.save(courier);
    }
    @Override
    public void updateLocation(Integer courierId, GpsCoordinatesDto locationDto) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new RuntimeException("Courier not found"));

        courier.setLatitude(locationDto.getLatitude());
        courier.setLongitude(locationDto.getLongitude());

        courierRepository.save(courier);
    }
}
