package com.anateam.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anateam.entity.User;
import com.anateam.entity.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByFullName(String fullName);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByRole(UserRole role);

    List<User> findByCreatedAtBefore(OffsetDateTime date);

    @Modifying
    @Query("DELETE FROM User u WHERE u.isVerified = false AND u.createdAt < :cutoffTime")
    int deleteUnverifiedUsersOlderThan(@Param("cutoffTime") OffsetDateTime cutoffTime);
}
