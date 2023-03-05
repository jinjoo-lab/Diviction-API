package com.example.diviction.security.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(private val tokenProvider: TokenProvider) : OncePerRequestFilter() {

    companion object{
        const val AUTHORIZATION_HEADER = "Authorization"
    }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = resolveToken(request)

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            val authentication: Authentication = tokenProvider.getAuthentication(jwt!!)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request : HttpServletRequest) : String?
    {
        val token : String = request.getHeader(AUTHORIZATION_HEADER)
        if(StringUtils.hasText(token)&&token.startsWith("Bearer "))
        {
            return token.substring(7)
        }else
            return null
    }
}
