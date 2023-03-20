package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.dto.ResponseMemberDto
import com.example.diviction.module.account.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Member", description = "중독자 조회 및 접근")
@RestController
@RequestMapping("/member")
class MemberController (private val memberService: MemberService){
    @Operation(description = "중독자의 email로 조회 , 없을 경우 500 Error")
    @GetMapping("/get/email/{user_email}")
    fun getMemberByEamil(@PathVariable user_email : String) : ResponseMemberDto
    {
        val result  = memberService.getMemberByEamil(user_email)

        return result
    }

    @Operation(description = "중독자의 pk(long)으로 조회 , 없을 경우 500 error")
    @GetMapping("/get/{userId}")
    fun getMemberById(@PathVariable userId : Long) : ResponseMemberDto
    {
        val result  = memberService.getMemberById(userId)

        return result
    }

    @Operation(description = "해당 id의 중독자에 대한 매칭 정보 반환 (중독자는 최대 1개의 매칭 정보밖에 없음), 없을 경우 null 반환 (body가 없음) -> 모든 경우가 200OK")
    @GetMapping("/match/{id}")
    fun getMatchById(@PathVariable id : Long) : MatchResponseDto?
    {
        return  memberService.getMatchById(id)
    }
    @Operation(description = "중독자 id로 삭제")
    @GetMapping("/delete/{id}")
    fun deleteMember(@PathVariable id : Long){
        memberService.deleteMember(id)
    }
    @Operation(description = "모든 중독자 조회 리스트 반환 ( 없을 경우 빈 리스트 ) ")
    @GetMapping("/all")
    fun getAllMember() : List<ResponseMemberDto>
    {
        return memberService.getAllMember()
    }
}
