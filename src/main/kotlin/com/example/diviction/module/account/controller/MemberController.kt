package com.example.diviction.module.account.controller

import com.example.diviction.module.account.dto.MatchDto
import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.dto.MemberDto
import com.example.diviction.module.account.entity.Matching
import com.example.diviction.module.account.service.MemberService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/member")
class MemberController (private val memberService: MemberService){

    @GetMapping("/get/{userId}")
    fun getMember(@PathVariable userId : Long) : MemberDto
    {
        val result : MemberDto = memberService.getMember(userId)

        return result
    }

    @PostMapping("/save")
    fun saveMember(@Valid @RequestBody memberDto: MemberDto)
    {
        memberService.saveMember(memberDto)
    }

    @GetMapping("/match/{id}")
    fun getMatchById(@PathVariable id : Long) : MatchResponseDto
    {
        return  memberService.getMatchById(id)
    }

    @GetMapping("/delete/{id}")
    fun deleteMember(@PathVariable id : Long){
        memberService.deleteMember(id)
    }
}
