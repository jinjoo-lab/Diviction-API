package com.example.diviction.module.account.controller


import com.example.diviction.module.account.dto.*
import com.example.diviction.module.account.service.AuthService
import com.example.diviction.security.constants.Authority
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name="Auth", description = "회원가입 , 로그인 , 토큰 확인(자동 로그인)")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @Operation(description = "토큰 발급")
    @PostMapping("/validate/token")
    fun validateToken(autoLoginDto: AutoLoginDto) : ResponseEntity<TokenDto?>
    {
        return authService.refreshToken(autoLoginDto)
    }
    @Operation(description = "중독자 회원 가입 , 성공시 200 OK, 회원 아이디 포함한 정보 반환, 실패시 500 error")
    @PostMapping("/signUp/member")
    fun signUpMember(@RequestBody memberDto : MemberDto) : ResponseEntity<SignUpMemberDto>
    {
        return ResponseEntity(authService.signUpMember(memberDto) , HttpStatus.OK)
    }

    @Operation(description = "중독자 로그인 , 성공시 : 200 ok, 토큰  , 실패시 401 error")
    @PostMapping("signIn/member")
    fun signInMember(@RequestBody loginDto: LoginDto) : TokenDto
    {
        return authService.signInMember(loginDto.email,loginDto.password,loginDto.authority)
    }
    @Operation(description = "상담자 회원 가입 , 성공시 200 OK, 회원 아이디 포함한 정보 반환, 실패시 500 error")
    @PostMapping("/signUp/counselor")
    fun signUpCounselor(@RequestBody counselorDto: CounselorDto) : ResponseEntity<SignUpCounselorDto>
    {
        return ResponseEntity(authService.signUpCounselor(counselorDto), HttpStatus.OK)
    }
    @Operation(description = "상담자 로그인 , 성공시 : 200 ok, 토큰  , 실패시 401 error")
    @PostMapping("signIn/counselor")
    fun signInCounselor(@RequestBody loginDto: LoginDto): TokenDto
    {
        return authService.signInMember(loginDto.email,loginDto.password,loginDto.authority)
    }

    @Operation(description = "이메일 중복 체크 , url path 사용 : check/email/{email}/role/{role} , 결과 boolean")
    @GetMapping("check/email/{email}/role/{role}")
    fun checkEmailDuplication(@PathVariable email : String , @PathVariable role : Authority) : Boolean
    {
        return authService.checkEmailDuplication(email,role)
    }
}
