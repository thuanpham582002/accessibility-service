package dev.noroom113.accessibility_service.repository;

import dev.noroom113.accessibility_service.entity.Accessibility
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccessibilityRepository : JpaRepository<Accessibility, Long> {
}