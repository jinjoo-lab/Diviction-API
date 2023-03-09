package com.example.diviction.module.account.service

import com.example.diviction.module.account.dto.CounselorDto
import com.example.diviction.module.account.dto.MemberDto
import com.example.diviction.module.account.dto.TokenDto
import com.example.diviction.module.account.entity.Counselor
import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.account.repository.CounselorRepository
import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.security.constants.Authority
import com.example.diviction.security.entity.RefreshToken
import com.example.diviction.security.jwt.TokenProvider
import com.example.diviction.security.repository.RefreshTokenRepository
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val counselorRepository: CounselorRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenProvider: TokenProvider,
    private val authenticationManagerBuilder : AuthenticationManagerBuilder,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger = LoggerFactory.getLogger(AuthService::class.java)

    fun validateToken(token : String) : Boolean
    {
        return tokenProvider.validateToken(token)
    }

    fun signUpMember(memberDto : MemberDto)
    {
        val member : Member = Member(
            memberDto.email,passwordEncoder.encode(memberDto.password),memberDto.name,memberDto.birth,memberDto.address,
            memberDto.gender,memberDto.profile_img_url,Authority.ROLE_USER
        )

        memberRepository.save(member)
    }

    fun signInMember(email  : String, password : String,role : Authority) : TokenDto
    {
        logger.info("member login start")
        val credit = UsernamePasswordAuthenticationToken(email,password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(credit)
        logger.info("authentication : " + authentication.toString())
        return tokenProvider.createTokenDto(authentication,role).also{
            refreshTokenRepository.save(RefreshToken(email,it.refreshToken))
        }
    }

    fun signInCounselor(email  : String, password : String,role : Authority) : TokenDto
    {
        logger.info("counselor login start")
        val credit = UsernamePasswordAuthenticationToken(email,password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(credit)
        logger.info("authentication : " + authentication.toString())
        return tokenProvider.createTokenDto(authentication,role).also{
            refreshTokenRepository.save(RefreshToken(email,it.refreshToken))
        }
    }

    fun signUpCounselor(counselorDto: CounselorDto)
    {
        val counselor : Counselor = Counselor(
            counselorDto.email,passwordEncoder.encode(counselorDto.password),counselorDto.name,counselorDto.birth,
            counselorDto.address,counselorDto.gender,counselorDto.profile_img_url,
            Authority.ROLE_COUNSELOR
        )

        counselorRepository.save(counselor)
    }

    fun refreshToken(accessToken : String , refreshToken : String,authority: Authority) : TokenDto
    {
        if(!tokenProvider.validateToken(refreshToken))
        {
            throw RuntimeException("유효하지 않은 토큰입니다")
        }

        val authentication = try {
            tokenProvider.getAuthentication(accessToken)
        }catch (e : EntityNotFoundException)
        {
            throw RuntimeException("일치하는 정보가 없습니다.")
        }

        val userEmail = authentication.name
        if (getRefreshToken(userEmail).tokenValue != refreshToken) {
            throw RuntimeException("토큰 정보가 일치하지 않습니다")
        }

        return tokenProvider.createTokenDto(authentication,authority).also {
            refreshTokenRepository.save(RefreshToken(userEmail, it.refreshToken))
        }
    }

    fun getRefreshToken(userEmail : String) : RefreshToken
    {
        return refreshTokenRepository.getById(userEmail)
    }
}
