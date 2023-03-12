package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.*
import com.example.diviction.module.account.service.AuthService
import com.example.diviction.security.constants.Authority
import org.springframework.http.ResponseEntity
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
    fun validateToken(autoLoginDto: AutoLoginDto) : ResponseEntity<TokenDto?>
    {
        return authService.refreshToken(autoLoginDto)
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

    @PostMapping("signIn/counselor")
    fun signInCounselor(@RequestBody loginDto: LoginDto): TokenDto
    {
        return authService.signInMember(loginDto.email,loginDto.password,loginDto.authority)
    }

}
