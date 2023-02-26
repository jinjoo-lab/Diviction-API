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
    fun getMember(email : String) : MemberDto
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

    fun getMember(userId : Long) : MemberDto
    {
        val cur = memberRepository.findById(userId)

        if(cur.isPresent)
        {
            val member = cur.get()

            return member.toDto()
        }
        else{
            throw RuntimeException("해당 Id의 사용자는 존재하지 않습니다.")
        }
    }

    fun saveMember(memberDto: MemberDto)
    {
        if(memberRepository.findByEmail(memberDto.email).isPresent)
        {
            throw RuntimeException("해당 이메일의 사용자는 이미 존재합니다.")
        }

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

    fun getMatchById(id : Long) : MatchResponseDto
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

        throw RuntimeException()
    }

    fun deleteMember(id : Long)
    {
        memberRepository.deleteById(id)
    }

    fun Member.toDto() : MemberDto = MemberDto(
        email, password, name, birth, address, gender, profile_img_url
    )
}
