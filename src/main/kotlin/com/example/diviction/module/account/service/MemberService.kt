package com.example.diviction.module.account.service

import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.dto.MemberDto
import com.example.diviction.module.account.dto.SignUpMemberDto
import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.account.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(private val memberRepository: MemberRepository){
    fun getMemberById(id : Long) : SignUpMemberDto{
        val member = memberRepository.getById(id)

        return member.toResponseDto()
    }

    fun getMemberByEamil(email : String) : SignUpMemberDto
    {
        val cur = memberRepository.findByEmail(email)

        if(cur.isPresent)
        {
            val member = cur.get()

            return member.toResponseDto()
        }
        else{
            throw RuntimeException("해당 이메일의 사용자는 존재하지 않습니다.")
        }
    }

    fun getMatchById(id : Long) : MatchResponseDto?
    {
        var cur = memberRepository.findById(id)

        if(cur.isPresent)
        {
            var member = cur.get()
            var match =  member.matching

            if(match!=null)
            {
                return MatchResponseDto(matchId = match.id, counselorId = match.counselor.id,counselorEmail = match.counselor.email,patientId = match.patient.id ,patientEmail = match.patient.email)
            }
        }

        return null
    }

    fun deleteMember(id : Long)
    {
        memberRepository.deleteById(id)
    }

    fun Member.toDto() : MemberDto = MemberDto(
        email, password, name, birth, address, gender, profile_img_url
    )

    fun Member.toResponseDto() : SignUpMemberDto = SignUpMemberDto(id!!,email, password, name, birth, address, gender, profile_img_url)

    fun getAllMember() : List<SignUpMemberDto>
    {
        val members = memberRepository.findAll()
        val list : MutableList<SignUpMemberDto> = mutableListOf()

        members.forEach {
            list.add(it.toResponseDto())
        }

        return list
    }
}
