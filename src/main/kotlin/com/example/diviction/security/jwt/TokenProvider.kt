package com.example.diviction.security.jwt



import com.example.diviction.module.account.dto.TokenDto
import com.example.diviction.security.constants.Authority
import com.example.diviction.security.service.MemberDetailService
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import kotlin.RuntimeException


@Component
class TokenProvider(
    @Value("\${jwt.secret}") secretKey : String,
    private val memberDetailService : MemberDetailService,
    private val counselorDetailService: MemberDetailService
) {
    private val key: Key
    private val logger: Logger = LoggerFactory.getLogger("JWTTOKEN")

    companion object {
        private const val ACCESS_TOKEN_EXPIRE_TIME: Long = (1000 * 60 * 30).toLong() // 30minute
        private const val REFRESH_TOKEN_EXPIRE_TIME: Long = (1000 * 60 * 60 * 24 * 7).toLong() // 7days
    }

    init {
        logger.info("토큰 초기화")
        val keyBytes: ByteArray = Decoders.BASE64.decode(secretKey)
        key = Keys.hmacShaKeyFor(keyBytes)
        logger.info("토큰 초기화 완료")
    }

    fun createTokenDto(userEmail: String, role: Authority): TokenDto {
        logger.info("토큰 생성")
        val now = Date().time
        val accessExpired = Date(now + ACCESS_TOKEN_EXPIRE_TIME)
        val accessToken: String = Jwts.builder()
            .claim("email", userEmail)
            .claim("role", role)
            .setExpiration(accessExpired)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken: String = Jwts.builder()
            .setExpiration(Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        return TokenDto(
            accessToken,
            refreshToken,
            accessExpired.time
        )
    }

    fun getAuthentication(accessToken : String) : Authentication
    {
        val claim = parseClaims(accessToken)

        if(claim["role"]==null)
        {
            throw RuntimeException("권한 정보가 없는 토큰입니다.")
        }

        val role = claim["role"] as String
        logger.info(role)


        if(role.equals("ROLE_COUNSELOR"))
        {
            val counselorDetail = counselorDetailService.loadUserByUsername(claim["email"] as String)
            logger.info("email claim : "+claim["email"] as String)
            return UsernamePasswordAuthenticationToken(claim["email"],"",counselorDetail.authorities)
        }
        else if(role.equals("ROLE_USER"))
        {
            val memberDetail = memberDetailService.loadUserByUsername(claim["email"] as String)
            logger.info("email claim : "+claim["email"] as String)
            return UsernamePasswordAuthenticationToken(claim["email"],"",memberDetail.authorities)
        }
        else{
            throw RuntimeException("당신은 관리자 ???")
        }
    }

    fun validateToken(token : String?) : Boolean
    {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            logger.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            logger.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            logger.info("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }
    fun parseClaims(accessToken : String) : Claims
    {
        return try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
        }catch (e : ExpiredJwtException)
        {
            e.claims
        }
    }
}
