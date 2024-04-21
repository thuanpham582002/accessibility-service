package dev.noroom113.accessibility_service.controller

import dev.noroom113.accessibility_service.entity.Accessibility
import dev.noroom113.accessibility_service.service.AccessibilityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accessibility")
class AccessibilityController(
    private val accessibilityService: AccessibilityService
) {

    @PostMapping("/add")
    fun addAccessibility(@RequestBody accessibility: Accessibility) {
        accessibilityService.addAccessibility(accessibility)
    }

    @GetMapping("/get")
    fun getAccessibilityById(@RequestBody id: Long): ResponseEntity<Accessibility> {
        return ResponseEntity.ok(accessibilityService.getAccessibilityById(id))
    }

    @GetMapping("/all")
    fun getAccessibilities(): ResponseEntity<List<Accessibility>> {
        return ResponseEntity.ok(accessibilityService.getAccessibilities())
    }

    @PutMapping("/update")
    fun updateAccessibility(accessibility: Accessibility) {
        accessibilityService.updateAccessibility(accessibility)
    }

    @PostMapping("/delete")
    fun deleteAccessibility(id: Long) {
        accessibilityService.deleteAccessibility(id)
    }
}
