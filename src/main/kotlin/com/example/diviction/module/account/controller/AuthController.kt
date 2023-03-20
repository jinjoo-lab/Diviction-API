package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.*
import com.example.diviction.module.account.service.AuthService
import com.example.diviction.security.constants.Authority
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Encoding
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Tag(name = "Auth", description = "회원 가입 , 로그인 , 토큰 확인(자동 로그인)")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,

    ) {
    @Operation(description = "토큰 발급")
    @PostMapping("/validate/token")
    fun validateToken(@RequestBody autoLoginDto: AutoLoginDto): ResponseEntity<TokenDto?> {
        return authService.refreshToken(autoLoginDto)
    }

    @Operation(description = "중독자 회원 가입 , 성공시 200 OK, 회원 아이디 포함한 정보 반환, 실패시 500 error")
    @PostMapping("/signUp/member", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    fun signUpMember(
        @RequestPart(value = "requestMemberDto") requestMemberDto: RequestMemberDto,
        @RequestPart(name = "image file") multipartFile: MultipartFile?
    ): ResponseEntity<ResponseMemberDto> {
        return ResponseEntity(authService.signUpMember(requestMemberDto,multipartFile), HttpStatus.OK)
    }

    @Operation(description = "중독자 로그인 , 성공시 : 200 ok, 토큰  , 실패시 401 error")
    @PostMapping("signIn/member")
    fun signInMember(@RequestBody loginDto: LoginDto): TokenResult {
        return authService.signInMember(loginDto.email, loginDto.password, loginDto.authority)
    }

    @Operation(description = "상담자 회원 가입 , 성공시 200 OK, 회원 아이디 포함한 정보 반환, 실패시 500 error")
    @PostMapping(
        "/signUp/counselor",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE]
    )
    fun signUpCounselor(
        @RequestPart(value = "requestCounselorDto") requestCounselorDto: RequestCounselorDto,
        @RequestPart(name = "image file") multipartFile: MultipartFile?
    ): ResponseEntity<ResponseCounselorDto> {
        return ResponseEntity(authService.signUpCounselor(requestCounselorDto,multipartFile), HttpStatus.OK)
    }

    @Operation(description = "상담자 로그인 , 성공시 : 200 ok, 토큰  , 실패시 401 error")
    @PostMapping("signIn/counselor")
    fun signInCounselor(@RequestBody loginDto: LoginDto): TokenResult {
        return authService.signInMember(loginDto.email, loginDto.password, loginDto.authority)
    }

    @Operation(description = "이메일 중복 체크 , url path 사용 : check/email/{email}/role/{role} , 결과 boolean")
    @GetMapping("check/email/{email}/role/{role}")
    fun checkEmailDuplication(@PathVariable email: String, @PathVariable role: Authority): Boolean {
        return authService.checkEmailDuplication(email, role)
    }
}
