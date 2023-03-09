package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.*
import com.example.diviction.module.account.service.AuthService
import com.example.diviction.security.constants.Authority
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/validate/token")
    fun validateToken(token : String) : Boolean
    {
        return authService.validateToken(token)
    }

    @PostMapping("/signUp/member")
    fun signUpMember(@RequestBody memberDto : MemberDto)
    {
        authService.signUpMember(memberDto)
    }

    @PostMapping("signIn/member")
    fun signInMember(@RequestBody loginDto: LoginDto) : TokenDto
    {
        return authService.signInMember(loginDto.email,loginDto.password,loginDto.authority)
    }

    @PostMapping("/signUp/counselor")
    fun signUpCounselor(@RequestBody counselorDto: CounselorDto)
    {
        authService.signUpCounselor(counselorDto)
    }

    @PostMapping("signIn/counsleor")
    fun signInCounselor(@RequestBody loginDto: LoginDto): TokenDto
    {
        return authService.signInMember(loginDto.email,loginDto.password,loginDto.authority)
    }

    @PostMapping("/refresh/member")
    fun refreshTokenUser(@RequestBody refreshTokenDto: RefreshTokenDto) : TokenDto
    {
        return authService.refreshToken(refreshTokenDto.accessToken, refreshTokenDto.refreshToken,Authority.ROLE_USER)
    }

    @PostMapping("/refresh/counselor")
    fun refreshTokenCounselor(@RequestBody refreshTokenDto: RefreshTokenDto) : TokenDto
    {
        return authService.refreshToken(refreshTokenDto.accessToken, refreshTokenDto.refreshToken,Authority.ROLE_USER)
    }
}
