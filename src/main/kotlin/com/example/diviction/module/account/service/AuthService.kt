package com.example.diviction.module.account.service

import com.example.diviction.infra.gcp.GCP_URLs.COUNSELOR_BASIC_IMG_URL
import com.example.diviction.infra.gcp.GCP_URLs.PATIENT_BASIC_IMG_URL
import com.example.diviction.infra.gcp.GcpStorageService
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
import org.springframework.web.multipart.MultipartFile


@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val counselorRepository: CounselorRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenProvider: TokenProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val passwordEncoder: PasswordEncoder,
    private val gcpStorageService: GcpStorageService
) {
    private val logger = LoggerFactory.getLogger(AuthService::class.java)
    fun Member.toResponseDto(): ResponseMemberDto =
        ResponseMemberDto(id!!, email, password, name, birth, address, gender, profile_img_url)

    fun Counselor.toResponseDto(): ResponseCounselorDto =
        ResponseCounselorDto(id!!, email, password, name, birth, address, gender, profile_img_url, confirm)

    fun signUpMember(requestMemberDto: RequestMemberDto): ResponseMemberDto {
        // 저장 후 아래 "" (profile_img_url) 채우기
        val imgUrl = if(requestMemberDto.multipartFile == null){
            PATIENT_BASIC_IMG_URL
        } else {
            gcpStorageService.uploadFileToGCS(requestMemberDto.multipartFile!!)
        }

        val member: Member = Member(
            requestMemberDto.email,
            passwordEncoder.encode(requestMemberDto.password),
            requestMemberDto.name,
            requestMemberDto.birth,
            requestMemberDto.address,
            requestMemberDto.gender,
            imgUrl,
            Authority.ROLE_USER
        )

        val result = memberRepository.save(member)

        return result.toResponseDto()
    }

    fun signInMember(email: String, password: String, role: Authority): TokenResult {
        logger.info("member login start")
        val credit = UsernamePasswordAuthenticationToken(email, password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(credit)
        logger.info("authentication : " + authentication.toString())

        val token = tokenProvider.createTokenDto(authentication, role).also {
            refreshTokenRepository.save(RefreshToken(email, it.refreshToken))
        }

        return TokenResult(authentication.name, token)
    }

    fun signInCounselor(email: String, password: String, role: Authority): TokenResult {
        logger.info("counselor login start")
        val credit = UsernamePasswordAuthenticationToken(email, password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(credit)
        logger.info("authentication : " + authentication.toString())
        val token = tokenProvider.createTokenDto(authentication, role).also {
            refreshTokenRepository.save(RefreshToken(email, it.refreshToken))
        }

        return TokenResult(authentication.name, token)
    }


    fun signUpCounselor(requestCounselorDto: RequestCounselorDto): ResponseCounselorDto {
        // 저장 후 아래 "" (profile_img_url) 채우기
        val imgUrl = if(requestCounselorDto.multipartFile == null){
            COUNSELOR_BASIC_IMG_URL
        } else {
            gcpStorageService.uploadFileToGCS(requestCounselorDto.multipartFile!!)
        }

        val counselor: Counselor = Counselor(
            requestCounselorDto.email,
            passwordEncoder.encode(requestCounselorDto.password),
            requestCounselorDto.name,
            requestCounselorDto.birth,
            requestCounselorDto.address,
            requestCounselorDto.gender,
            imgUrl,
            Authority.ROLE_COUNSELOR
        )

        val result = counselorRepository.save(counselor)

        return result.toResponseDto()
    }

    // header에 보내도록 수정
    fun refreshToken(autoLoginDto: AutoLoginDto): ResponseEntity<TokenDto?> {
        var header: HttpHeaders = HttpHeaders()
        header.contentType = MediaType("application", "json", Charsets.UTF_8)

        try {
            if (!tokenProvider.validateToken(autoLoginDto.refreshToken) || !refreshTokenRepository.existsByTokenValue(
                    autoLoginDto.refreshToken
                )
            ) {
                println("wrong refresh token")
                return ResponseEntity(null, header, HttpStatus.UNAUTHORIZED)
            }
        } catch (e: ExpiredJwtException) {
            println("expired refresh jwt exception")
            header.set("CODE", "RTE")
            return ResponseEntity(null, header, HttpStatus.UNAUTHORIZED)
        }

        try {
            if (!tokenProvider.validateToken(autoLoginDto.accessToken)) {
                println("wrong access token")
                return ResponseEntity(null, header, HttpStatus.UNAUTHORIZED)
            }
        } catch (e: ExpiredJwtException) {
            println("expired access jwt exception")
            val authentication = tokenProvider.getAuthentication(autoLoginDto.refreshToken)
            val accessToken = tokenProvider.createAccessToken(authentication, autoLoginDto.authority)
            header.set("Authorization", "Bearer " + accessToken.accessToken)
            header.set("RT", autoLoginDto.refreshToken)
            return ResponseEntity(null, header, HttpStatus.OK)
        }

        return ResponseEntity(null, header, HttpStatus.OK)
    }

    fun getRefreshToken(userEmail: String): RefreshToken {
        return refreshTokenRepository.getById(userEmail)
    }

    fun checkEmailDuplication(email: String, role: Authority): Boolean {
        if (role == Authority.ROLE_USER) {
            return !memberRepository.existsByEmail(email)
        } else if (role == Authority.ROLE_COUNSELOR) {
            return !counselorRepository.existsByEmail(email)
        }

        return false
    }

}
