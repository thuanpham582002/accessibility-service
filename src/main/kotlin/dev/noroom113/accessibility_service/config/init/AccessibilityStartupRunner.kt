package dev.noroom113.accessibility_service.config.init

import dev.noroom113.accessibility_service.entity.Accessibility
import dev.noroom113.accessibility_service.entity.UrlAccessable
import dev.noroom113.accessibility_service.service.AccessibilityService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class AccessibilityStartupRunner(
    private val accessibilityService: AccessibilityService
) : CommandLineRunner {
    override fun run(vararg args: String?) {

        val adminAccessibility = accessibilityService.addAccessibility(
            Accessibility(
                "admin",
                "This is the admin accessibility, you can do anything",
                listOf(
                    UrlAccessable(
                        "ALL",
                        "/api/v1/**"
                    )
                ),
            )
        )

        val guestAccessibility = accessibilityService.addAccessibility(
            Accessibility(
                "guest",
                "test",
                listOf(
                    UrlAccessable(
                        "GET",
                        "/api/v1/car/all"
                    ),
                    UrlAccessable(
                        "GET",
                        "/api/v1/accessibility/{id}"
                    ),
                )
            )
        )


        val staffAccessibility = accessibilityService.addAccessibility(
            Accessibility(
                "staff",
                "This is the staff accessibility, you can do anything except delete",
                listOf(
                    UrlAccessable(
                        "ALL",
                        "/api/v1/contract/**"
                    )
                ),
                listOf(
                    guestAccessibility
                )
            )
        )

        val authenLevel1Accessibility = accessibilityService.addAccessibility(
            Accessibility(
                "authen_level_1",
                "This is the first level of authentication",
                listOf(
                    UrlAccessable(
                        "GET",
                        "/api/v1/contract/**"
                    ),
                ),
                listOf(
                    guestAccessibility
                )
            )
        )
        println(accessibilityService.getAccessibilities())
    }
}