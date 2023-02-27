package com.example.diviction.module.consulting.service

import com.example.diviction.module.account.repository.CounselorRepository
import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.consulting.dto.ConsultRequestDto
import com.example.diviction.module.consulting.dto.ConsultResponseDto
import com.example.diviction.module.consulting.entity.Consulting
import com.example.diviction.module.consulting.repository.ConsultingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConsultingService(
    private val memberRepository: MemberRepository,
    private val counselorRepository: CounselorRepository,
    private val consultingRepository: ConsultingRepository
) {

    @Transactional
    fun saveConsultingLog(consultingRequestDto: ConsultRequestDto)
    {
        val patient = memberRepository.getById(consultingRequestDto.patient_id)
        val counselor = counselorRepository.getById(consultingRequestDto.conuselor_id)

        consultingRepository.save(Consulting(
            patient,counselor,consultingRequestDto.content,consultingRequestDto.date
        ))
    }

    fun Consulting.toResponseDto() = ConsultResponseDto(
        consultCounselor.id!!,content,date
    )

}
