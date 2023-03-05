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

    fun signUpMember(memberDto : MemberDto)
    {
        val member : Member = Member(
            memberDto.email,passwordEncoder.encode(memberDto.password),memberDto.name,memberDto.birth,memberDto.address,
            memberDto.gender,memberDto.profile_img_url,Authority.ROLE_USER
        )

        memberRepository.save(member)
    }

    fun signInMember(email  : String, password : String) : TokenDto
    {
        logger.info("member login start")
        val member : Member = memberRepository.getByEmail(email) ?: throw RuntimeException("해당 Email 의 사용자를 찾을 수 없습니다.")

        if(passwordEncoder.matches(password,member.password))
        {
            val tokenDto = tokenProvider.createTokenDto(email,Authority.ROLE_USER)
            logger.info(tokenDto.refreshToken)
            refreshTokenRepository.save(RefreshToken(email,tokenDto.refreshToken))
            logger.info("member login success")
            return tokenDto
        }

        else throw RuntimeException("해당 Member 정보는 존재하지 않습니다.")
    }

    fun signUpCounselor(counselorDto: CounselorDto)
    {
        val counselor : Counselor = Counselor(
            counselorDto.email,counselorDto.password,counselorDto.name,counselorDto.birth,
            counselorDto.address,counselorDto.gender,counselorDto.profile_img_url,
            Authority.ROLE_COUNSELOR
        )

        counselorRepository.save(counselor)
    }
}
