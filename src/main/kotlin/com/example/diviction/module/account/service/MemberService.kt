package com.example.diviction.module.account.service

import com.example.diviction.module.account.dto.MatchDto
import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.dto.MemberDto
import com.example.diviction.module.account.entity.Matching
import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.account.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(private val memberRepository: MemberRepository){
    fun getMemberById(id : Long) : MemberDto{
        val member = memberRepository.getById(id)

        return member.toDto()
    }

    fun getMemberByEamil(email : String) : MemberDto
    {
        val cur = memberRepository.findByEmail(email)

        if(cur.isPresent)
        {
            val member = cur.get()

            return member.toDto()
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
                return MatchResponseDto(counselorEmail = match.counselor.email, patientEmail = match.patient.email)
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

    fun getAllMember() : List<MemberDto>
    {
        val members = memberRepository.findAll()
        val list : MutableList<MemberDto> = mutableListOf()

        members.forEach {
            list.add(it.toDto())
        }

        return list
    }
}
