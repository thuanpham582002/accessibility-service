package dev.noroom113.accessibility_service.jwt.interceptor

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

interface JwtInterceptor {
    fun intercept(request: HttpServletRequest, response: HttpServletResponse)
}
