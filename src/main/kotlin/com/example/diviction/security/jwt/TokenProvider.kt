package com.example.diviction.security.jwt



import com.example.diviction.module.account.dto.TokenDto
import com.example.diviction.security.constants.Authority
import com.example.diviction.security.entity.RefreshToken
import com.example.diviction.security.repository.RefreshTokenRepository
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
    private val counselorDetailService: MemberDetailService,
    private val refreshTokenRepository : RefreshTokenRepository
) {
    private val key: Key
    private val logger: Logger = LoggerFactory.getLogger("JWTTOKEN")

    companion object {
        // test
        private const val ACCESS_TOKEN_EXPIRE_TIME: Long = (1000* 60 * 30).toLong() // 30minute * 60 * 30
        private const val REFRESH_TOKEN_EXPIRE_TIME: Long = (1000 * 60 * 60 * 24 * 7).toLong() // 7days
    }

    init {
        logger.info("토큰 초기화")
        val keyBytes: ByteArray = Decoders.BASE64.decode(secretKey)
        key = Keys.hmacShaKeyFor(keyBytes)
        logger.info("토큰 초기화 완료")
    }

    fun checkRefreshToken(refreshToken : String) : Boolean
    {
        return refreshTokenRepository.existsByTokenValue(refreshToken)
    }

    fun createTokenDto(authentication: Authentication, role: Authority): TokenDto {
        logger.info("토큰 생성")
        logger.info(authentication.name)
        val now = Date().time
        val accessExpired = Date(now + ACCESS_TOKEN_EXPIRE_TIME)
        val accessToken: String = Jwts.builder()
            .setSubject(authentication.name)
            .claim("role", role)
            .setExpiration(accessExpired)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken: String = Jwts.builder()
            .setSubject(authentication.name)
            .claim("role", role)
            .setExpiration(Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        return TokenDto(
            accessToken,
            refreshToken,
            accessExpired.time
        )
    }

    fun createRefreshTokenDto(authentication: Authentication,role : Authority) : TokenDto
    {
        val now = Date().time
        val accessExpired = Date(now + ACCESS_TOKEN_EXPIRE_TIME)
        val accessToken: String = Jwts.builder()
            .setSubject(authentication.name)
            .claim("role", role)
            .setExpiration(accessExpired)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken: String = Jwts.builder()
            .setSubject(authentication.name)
            .claim("role", role)
            .setExpiration(Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        refreshTokenRepository.save(RefreshToken(authentication.name,refreshToken))

        return TokenDto(
            accessToken,
            refreshToken,
            accessExpired.time
        )
    }

    fun getRole(token: String) : Authority
    {
        val claim = parseClaims(token)

        val role = claim["role"] as String

        if(role.equals("ROLE_USER"))
            return Authority.ROLE_USER

        else if(role.equals("ROLE_COUNSELOR"))
            return Authority.ROLE_COUNSELOR

        else throw RuntimeException("that token cannot have ROLE!!")
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
            val authorities: MutableList<GrantedAuthority> = mutableListOf()
            authorities.add(SimpleGrantedAuthority("ROLE_COUNSELOR"))
            return UsernamePasswordAuthenticationToken(claim.subject,"",authorities)
        }
        else if(role.equals("ROLE_USER"))
        {
            val authorities: MutableList<GrantedAuthority> = mutableListOf()
            authorities.add(SimpleGrantedAuthority("ROLE_USER"))
            return UsernamePasswordAuthenticationToken(claim.subject,"",authorities)
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
