package com.anateam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anateam.entity.Courier;
import com.anateam.entity.CourierStatus;
import com.anateam.entity.UserRole;
import com.anateam.entity.VehincleType;

public interface CourierRepository extends JpaRepository<Courier, Integer> {

    Optional<Courier> findByUserPhoneNumber(String phoneNumber);

    List<Courier> findByUserFullNameContaining(String namePart);

    List<Courier> findAllByStatusAndUserRole(CourierStatus status, UserRole role);

    List<Courier> findAllByVehicleType(VehincleType vehicleType);
}
