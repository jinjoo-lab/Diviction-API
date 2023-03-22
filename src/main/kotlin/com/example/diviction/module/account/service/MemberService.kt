package com.example.diviction.module.account.service

import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.dto.ResponseMemberDto
import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.account.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MemberService(private val memberRepository: MemberRepository){
    fun getMemberById(id : Long) : ResponseMemberDto{
        val member = memberRepository.getById(id)

        return member.toResponseDto()
    }

    fun getMemberByEamil(email : String) : ResponseMemberDto
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
                return MatchResponseDto(matchId = match.id, counselorId = match.counselor.id,counselorEmail = match.counselor.email, member = match.patient.toResponseDto())
            }
        }

        return null
    }

    fun deleteMember(id : Long)
    {
        memberRepository.deleteById(id)
    }

    fun Member.toResponseDto() : ResponseMemberDto = ResponseMemberDto(id!!,email, password, name, birth, address, gender, profile_img_url)

    fun getAllMember() : List<ResponseMemberDto>
    {
        val members = memberRepository.findAll()
        val list : MutableList<ResponseMemberDto> = mutableListOf()

        members.forEach {
            list.add(it.toResponseDto())
        }

        return list
    }

    fun updateMemberImg(memberId : Long,multipartFile: MultipartFile)
    {
        var member = memberRepository.getById(memberId)
    }
}
