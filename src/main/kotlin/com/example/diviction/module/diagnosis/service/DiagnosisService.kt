package com.example.diviction.module.diagnosis.service

import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.diagnosis.dto.DiagnosisResultDto
import com.example.diviction.module.diagnosis.entity.DiagnosisResult
import com.example.diviction.module.diagnosis.repository.DiagnosisRepository
import org.springframework.stereotype.Service

@Service
class DiagnosisService(private val diagnosisRepository: DiagnosisRepository,
private val memberRepository: MemberRepository) {

    fun saveDiagnosisResult(diagnosisResultDto: DiagnosisResultDto)
    {
        var memebr = memberRepository.getByEmail(email = diagnosisResultDto.patientEmail)

        var result = DiagnosisResult(
            memebr!!,diagnosisResultDto.date,diagnosisResultDto.vP1,diagnosisResultDto.vP2,diagnosisResultDto.vP3
        )
        memebr.diagnosisList.add(result)
        diagnosisRepository.save(result)
    }


}
