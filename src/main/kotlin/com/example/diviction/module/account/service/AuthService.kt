package com.example.diviction.module.account.service

import com.example.diviction.module.account.dto.*
import com.example.diviction.module.account.entity.Counselor
import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.account.repository.CounselorRepository
import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.security.constants.Authority
import com.example.diviction.security.entity.RefreshToken
import com.example.diviction.security.jwt.TokenProvider
import com.example.diviction.security.repository.RefreshTokenRepository
import io.jsonwebtoken.ExpiredJwtException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


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
    fun Member.toResponseDto() : SignUpMemberDto = SignUpMemberDto(id!!,email, password, name, birth, address, gender, profile_img_url)
    fun Counselor.toResponseDto() : SignUpCounselorDto = SignUpCounselorDto(id!!,email, password, name, birth, address, gender, profile_img_url, confirm)
    fun signUpMember(memberDto : MemberDto) : SignUpMemberDto
    {
        val member : Member = Member(
            memberDto.email,passwordEncoder.encode(memberDto.password),memberDto.name,memberDto.birth,memberDto.address,
            memberDto.gender,memberDto.profile_img_url,Authority.ROLE_USER
        )

        val result = memberRepository.save(member)

        return result.toResponseDto()
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

    fun signUpCounselor(counselorDto: CounselorDto) : SignUpCounselorDto
    {
        val counselor : Counselor = Counselor(
            counselorDto.email,passwordEncoder.encode(counselorDto.password),counselorDto.name,counselorDto.birth,
            counselorDto.address,counselorDto.gender,counselorDto.profile_img_url,
            Authority.ROLE_COUNSELOR
        )

        val result = counselorRepository.save(counselor)

        return result.toResponseDto()
    }
    // header에 보내도록 수정
    fun refreshToken(autoLoginDto: AutoLoginDto) : ResponseEntity<TokenDto?>
    {
        var header : HttpHeaders = HttpHeaders()
        header.contentType = MediaType("application","json",Charsets.UTF_8)

        try {
            if(!tokenProvider.validateToken(autoLoginDto.refreshToken)||!refreshTokenRepository.existsByTokenValue(autoLoginDto.refreshToken))
            {
                println("wrong refresh token")
                return ResponseEntity(null,header, HttpStatus.UNAUTHORIZED)
            }
        }catch (e : ExpiredJwtException)
        {
            println("expired refresh jwt exception")
            header.set("CODE","RTE")
            return ResponseEntity(null,header, HttpStatus.UNAUTHORIZED)
        }

        try{
            if(!tokenProvider.validateToken(autoLoginDto.accessToken))
            {
                println("wrong access token")
                return ResponseEntity(null,header, HttpStatus.UNAUTHORIZED)
            }
        }catch (e : ExpiredJwtException)
        {
            println("expired access jwt exception")
            val authentication = tokenProvider.getAuthentication(autoLoginDto.refreshToken)
            val accessToken = tokenProvider.createAccessToken(authentication,autoLoginDto.authority)
            header.set("Authorization","Bearer "+accessToken.accessToken)
            header.set("RT",autoLoginDto.refreshToken)
            return ResponseEntity(null,header, HttpStatus.OK)
        }

        return ResponseEntity(null,header, HttpStatus.OK)
    }

    fun getRefreshToken(userEmail : String) : RefreshToken
    {
        return refreshTokenRepository.getById(userEmail)
    }

    fun checkEmailDuplication(email : String,role : Authority) : Boolean
    {
        if(role.equals(Authority.ROLE_USER))
        {
            if(memberRepository.existsByEmail(email)){
                return false}

            else
            {return true}
        }

        else if(role.equals(Authority.ROLE_COUNSELOR))
        {
            if(counselorRepository.existsByEmail(email)) {
                return false
            }
            else {return true}
        }

        return false
    }
}
