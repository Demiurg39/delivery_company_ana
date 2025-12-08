package com.anateam.config;

import java.time.OffsetDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.anateam.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UnverifiedUserCleanupTask {

    private final UserRepository userRepository;

    /**
     * Runs every hour to clean up unverified users older than 24 hours.
     * This prevents database pollution from incomplete registrations.
     */
    @Scheduled(fixedRate = 3600000) // Every 1 hour
    @Transactional
    public void cleanupUnverifiedUsers() {
        OffsetDateTime cutoffTime = OffsetDateTime.now().minusHours(24);

        int deletedCount = userRepository.deleteUnverifiedUsersOlderThan(cutoffTime);

        if (deletedCount > 0) {
            log.info("🧹 Cleaned up {} unverified user(s) older than 24 hours", deletedCount);
        }
    }
}
