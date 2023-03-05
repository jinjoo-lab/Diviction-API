package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.MemberDto
import com.example.diviction.module.account.dto.LoginDto
import com.example.diviction.module.account.dto.TokenDto
import com.example.diviction.module.account.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/signUp/member")
    fun signUpMember(@RequestBody memberDto : MemberDto)
    {
        authService.signUpMember(memberDto)
    }

    @PostMapping("signIn/member")
    fun signInMember(@RequestBody loginDto: LoginDto) : TokenDto
    {
        return authService.signInMember(loginDto.email,loginDto.password)
    }

}
