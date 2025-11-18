package com.anateam.repository;

import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anateam.entity.User;
import com.anateam.entity.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByFullName(String fullName);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByRole(UserRole role);

    List<User> findByCreatedAtBefore(OffsetTime date);
}
