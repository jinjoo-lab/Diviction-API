package com.example.diviction.module.account.service

import com.example.diviction.module.account.dto.MemberDto
import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.account.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(private val memberRepository: MemberRepository){
    fun getMember(userId : Long) : MemberDto
    {
        val cur = memberRepository.findById(userId)

        if(cur.isPresent)
        {
            val member = cur.get()

            return member.toDto()
        }
        else{
            throw RuntimeException()
        }
    }

    fun saveMember(memberDto: MemberDto)
    {
        val member : Member = Member(
            email = memberDto.email,
            password = memberDto.password,
            name = memberDto.name,
            birth = memberDto.birth,
            address = memberDto.address,
            gender =  memberDto.gender,
            profile_img_url =  memberDto.profile_img_url
        )

        memberRepository.save(member)
    }


    fun Member.toDto() : MemberDto = MemberDto(
        email, password, name, birth, address, gender, profile_img_url
    )
}
