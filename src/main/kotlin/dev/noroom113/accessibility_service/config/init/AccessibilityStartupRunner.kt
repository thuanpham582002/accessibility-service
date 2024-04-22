package dev.noroom113.accessibility_service.config.init

import dev.noroom113.accessibility_service.entity.Accessibility
import dev.noroom113.accessibility_service.entity.HttpMethod
import dev.noroom113.accessibility_service.entity.UrlAccessable
import dev.noroom113.accessibility_service.service.AccessibilityService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class AccessibilityStartupRunner(
    private val accessibilityService: AccessibilityService,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val guestAccessibility = accessibilityService.addAccessibility(
            Accessibility(
                1,
                "guest",
                "test",
                mutableSetOf(
                    UrlAccessable(
                        setOf(HttpMethod.ALL),
                        "/api/v1/car/all"
                    ),
                    UrlAccessable(
                        setOf(HttpMethod.ALL),
                        "/api/v1/accessibility/{id}"
                    ),
                )
            )
        )


        val staffAccessibility = accessibilityService.addAccessibility(
            Accessibility(
                2,
                "staff",
                "This is the staff accessibility, you can do anything except delete",
                mutableSetOf(
                    UrlAccessable(
                        setOf(HttpMethod.ALL),
                        "/api/v1/contract/**"
                    ),
                    UrlAccessable(
                        setOf(HttpMethod.ALL),
                        "/api/v1/car/**"
                    ),
                    UrlAccessable(
                        setOf(HttpMethod.GET),
                        "/api/v1/user/**"
                    ),
                )
            )
        )

        val authenLevel1Accessibility = accessibilityService.addAccessibility(
            Accessibility(
                3,
                "authen_level_1",
                "This is the first level of authentication",
                mutableSetOf(
                    UrlAccessable(
                        setOf(HttpMethod.ALL),
                        "/api/v1/contract/**"
                    ),
                )
            )
        )

        val adminAccessibility = accessibilityService.addAccessibility(
            Accessibility(
                9999,
                "admin",
                "This is the admin accessibility, you can do anything",
                mutableSetOf(
                    UrlAccessable(
                        setOf(HttpMethod.ALL),
                        "/**"
                    )
                ),
            )
        )
        staffAccessibility.childAccessibilities.add(guestAccessibility)
        authenLevel1Accessibility.childAccessibilities.add(guestAccessibility)
        accessibilityService.updateAccessibility(staffAccessibility)
        accessibilityService.updateAccessibility(authenLevel1Accessibility)
        println(accessibilityService.getAccessibilities())
    }
}