package com.anateam.service;

import com.anateam.dto.CourierProfileDto;
import com.anateam.dto.GpsCoordinatesDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourierService {

    Page<CourierProfileDto> findAll(Pageable pageable);

    CourierProfileDto findDtoById(Integer id);

    CourierProfileDto updateStatus(Integer courierId, String status);

    void updateLocation(Integer courierId, GpsCoordinatesDto locationDto);
}
