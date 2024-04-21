package dev.noroom113.accessibility_service.service

import dev.noroom113.accessibility_service.entity.Accessibility
import dev.noroom113.accessibility_service.repository.AccessibilityRepository
import org.springframework.stereotype.Service

@Service
class AccessibilityService(
    private val accessibilityRepository: AccessibilityRepository
) {
    fun addAccessibility(accessibility: Accessibility) : Accessibility {
        return accessibilityRepository.save(accessibility)
    }

    fun getAccessibilityById(id: Long): Accessibility {
        return accessibilityRepository.findById(id).get()
    }

    fun getAccessibilities(): List<Accessibility> {
        return accessibilityRepository.findAll()
    }

    fun updateAccessibility(accessibility: Accessibility) {
        accessibilityRepository.save(accessibility)
    }

    fun deleteAccessibility(id: Long) {
        accessibilityRepository.deleteById(id)
    }
}
