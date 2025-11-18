package com.anateam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anateam.dto.CourierProfileDto;
import com.anateam.dto.GpsCoordinatesDto;
import com.anateam.entity.Courier;
import com.anateam.entity.CourierStatus;
import com.anateam.entity.GpsCoordinates;
import com.anateam.entity.User;
import com.anateam.repository.CourierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    private CourierProfileDto toCourierProfileDto(Courier courier) {
        User user = courier.getUser();

        return new CourierProfileDto(
            user.getId(), user.getFullName(), user.getPhoneNumber(),
            courier.getStatus().name(), courier.getVehicleType().name());
    }

    @Override
    public Page<CourierProfileDto> findAll(Pageable pageable) {
        return courierRepository.findAll(pageable).map(
            this::toCourierProfileDto);
    }

    @Override
    public CourierProfileDto findDtoById(Integer courierId) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(
            () -> new RuntimeException("Courier not found"));
        return toCourierProfileDto(courier);
    }

    @Override
    public CourierProfileDto updateStatus(Integer courierId, String status) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(
            () -> new RuntimeException("Courier not found"));

        courier.setStatus(CourierStatus.valueOf(status.toUpperCase()));
        return toCourierProfileDto(courierRepository.save(courier));
    }

    @Override
    public void updateLocation(Integer courierId,
                               GpsCoordinatesDto locationDto) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(
            () -> new RuntimeException("Courier not found"));

        GpsCoordinates newCoordinates =
            new GpsCoordinates(locationDto.latitude(), locationDto.longitude());

        courier.setCurrentCoordinates(newCoordinates);

        courierRepository.save(courier);
    }
}
