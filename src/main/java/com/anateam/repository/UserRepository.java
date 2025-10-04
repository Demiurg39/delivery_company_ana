package com.anateam.repository;

import com.anateam.entity.User;
import com.anateam.entity.UserRole;
import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByFullName(String fullName);

    Optional<User> findPhoneNumber(String phoneNumber);

    List<User> findByRole(UserRole role);

    List<User> findByCreatedAtBefore(OffsetTime date);
}
