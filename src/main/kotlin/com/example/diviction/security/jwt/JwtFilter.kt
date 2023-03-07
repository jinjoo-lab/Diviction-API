package com.example.diviction.security.jwt

import io.jsonwebtoken.ExpiredJwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(private val tokenProvider: TokenProvider) : OncePerRequestFilter() {

    val logger : Logger = LoggerFactory.getLogger("JWT FILTER")
    companion object{
        const val AUTHORIZATION_HEADER = "Authorization"
        const val REFRESH_TOKEN = "RT"
        const val BEARER_PREFIX = "Bearer "
    }
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val jwt = resolveToken(request)

        try {
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                logger.info("correct token in")
                val authentication: Authentication = tokenProvider.getAuthentication(jwt!!)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }catch(e : ExpiredJwtException)
        {
            logger.info("in filter catch Expired exception")

            val refreshToken = request.getHeader(REFRESH_TOKEN)
            if(StringUtils.hasText(refreshToken)&& tokenProvider.validateToken(refreshToken))
            {
                if(tokenProvider.checkRefreshToken(refreshToken))
                {
                    val ROLE = tokenProvider.getRole(refreshToken)
                    val reauthentication : Authentication = tokenProvider.getAuthentication(refreshToken)

                    val reTokenDto = tokenProvider.createRefreshTokenDto(reauthentication,ROLE)

                    response.setHeader(REFRESH_TOKEN,reTokenDto.refreshToken)
                    response.setHeader(AUTHORIZATION_HEADER,"Bearer "+reTokenDto.accessToken)
                    SecurityContextHolder.getContext().authentication = reauthentication
                }
            }
            response.setHeader("code","ATE")
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken.substring(7)
        } else null
    }
}
