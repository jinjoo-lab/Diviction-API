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

        }catch(e : ExpiredJwtException) // 만료된 경우
        {
            logger.info("in filter catch Expired exception")

            val refreshToken = request.getHeader(REFRESH_TOKEN) // 리프레시 확인

            if(StringUtils.hasText(refreshToken)&&tokenProvider.validateToken(refreshToken))
            {
                if(tokenProvider.checkRefreshToken(refreshToken)) // 확인
                {
                    val ROLE = tokenProvider.getRole(refreshToken)
                    val reauthentication : Authentication = tokenProvider.getAuthentication(refreshToken)

                    val reTokenDto = tokenProvider.createRefreshTokenDto(reauthentication,ROLE)
                    // 새로운 토큰 생성

                    response.setHeader(REFRESH_TOKEN,reTokenDto.refreshToken)
                    response.setHeader(AUTHORIZATION_HEADER,"Bearer "+reTokenDto.accessToken)
                    SecurityContextHolder.getContext().authentication = reauthentication

                    // 200 성공 (헤더 : 새로운 토큰 , 바디 : 사용자 요청한 내용)
                }
            }
            // 401 -> 리프레시 토큰 2주가 지나서 만료 ( 로그아웃 )  -> error
            response.setHeader("code","ATE") // error response header code : ATE (로그아웃)
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
