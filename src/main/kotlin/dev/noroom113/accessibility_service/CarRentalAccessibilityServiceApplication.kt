package dev.noroom113.accessibility_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class CarRentalAccessibilityServiceApplication

fun main(args: Array<String>) {
    runApplication<CarRentalAccessibilityServiceApplication>(*args)
}
