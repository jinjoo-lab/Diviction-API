package com.example.diviction.module.account.service

import com.example.diviction.module.account.dto.MatchDto
import com.example.diviction.module.account.dto.MatchResponseDto
import com.example.diviction.module.account.dto.ResponseCounselorDto
import com.example.diviction.module.account.dto.ResponseMemberDto
import com.example.diviction.module.account.entity.Counselor
import com.example.diviction.module.account.entity.Matching
import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.account.repository.CounselorRepository
import com.example.diviction.module.account.repository.MatchRepository
import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.memo.dto.ResponseMemoDto
import com.example.diviction.module.memo.entity.Memo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MatchService(
    @Autowired private val memberRepository: MemberRepository,
    @Autowired private val counselorRepository: CounselorRepository,
    @Autowired private val matchRepository: MatchRepository
) {

    fun patientDuplication(id : Long) : Boolean
    {
        return matchRepository.existsByPatient(memberRepository.getById(id))
    }


    @Transactional
    fun saveMatch(matchDto : MatchDto) : MatchResponseDto
    {
        var curPatient = memberRepository.findById(matchDto.patientId)
        var curCounselor = counselorRepository.findById(matchDto.counselorId)

        if(curPatient.isPresent&&curCounselor.isPresent)
        {
            var patient = curPatient.get()
            var counselor = curCounselor.get()

            if(patientDuplication(patient.id!!))
            {
                throw RuntimeException()
            }
            else{
                var match : Matching = Matching(patient = patient,counselor = counselor)
                patient.matching = match
                counselor.matching_list.add(match)
                val cur = matchRepository.save(match)

                return MatchResponseDto(cur.id,cur.counselor.toResponseDto(),cur.patient.toResponseDto())
            }
        }

        else{
            throw RuntimeException()
        }
    }

    fun getMatchByMatchingId(id :Long) : MatchResponseDto
    {
        var cur = matchRepository.findById(id)

        if(cur.isPresent)
        {
            var match = cur.get()

            return MatchResponseDto(matchId = match.id, counselor = match.counselor.toResponseDto()
                , member = match.patient.toResponseDto())
        }

        else{
            throw RuntimeException()
        }
    }

    fun getAllMatch() : List<MatchResponseDto>
    {
        val match = matchRepository.findAll()

        val list : MutableList<MatchResponseDto> = mutableListOf()

        match.forEach {
            list.add(MatchResponseDto(it.id,it.counselor.toResponseDto(),it.patient.toResponseDto()))
        }

        return list
    }

    fun deleteMatch(id : Long)
    {
        matchRepository.deleteById(id)
    }

    fun Member.toResponseDto() : ResponseMemberDto = ResponseMemberDto(id!!,email, password, name, birth, address, gender, profile_img_url)
    fun Counselor.toResponseDto() : ResponseCounselorDto = ResponseCounselorDto(id!!,email, password, name, birth, address, gender, profile_img_url, confirm)

}
