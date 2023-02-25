package com.example.diviction.module.account.service

import com.example.diviction.module.account.dto.MatchDto
import com.example.diviction.module.account.entity.Matching
import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.account.repository.CounselorRepository
import com.example.diviction.module.account.repository.MatchRepository
import com.example.diviction.module.account.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MatchService(
    @Autowired private val memberRepository: MemberRepository,
    @Autowired private val counselorRepository: CounselorRepository,
    @Autowired private val matchRepository: MatchRepository
) {

    fun patientDuplication(patient : Member) : Boolean
    {
        return false
    }


    @Transactional
    fun saveMatch(matchDto : MatchDto)
    {
        var curPatient = memberRepository.findById(matchDto.patientId)
        var curCounselor = counselorRepository.findById(matchDto.counselorId)

        if(curPatient.isPresent&&curCounselor.isPresent)
        {
            var patient = curPatient.get()
            println(patient)
            var counselor = curCounselor.get()
            println(counselor)
            if(patientDuplication(patient))
            {
                throw RuntimeException()
            }
            else{
                matchRepository.save(Matching(patient = patient,counselor = counselor))
            }
        }

        else{
            throw RuntimeException()
        }
    }


}
