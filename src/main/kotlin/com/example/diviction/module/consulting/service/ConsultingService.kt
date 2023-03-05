package com.example.diviction.module.consulting.service

import com.example.diviction.module.account.repository.CounselorRepository
import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.consulting.dto.ConsultRequestDto
import com.example.diviction.module.consulting.dto.ConsultResponseDto
import com.example.diviction.module.consulting.dto.ConsultUpdateDto
import com.example.diviction.module.consulting.entity.Consulting
import com.example.diviction.module.consulting.repository.ConsultingRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody

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

        val consultLog = Consulting(
            patient,counselor,consultingRequestDto.content,consultingRequestDto.date
        )
        patient.consultingList.add(consultLog)
        counselor.consultingList.add(consultLog)
        consultingRepository.save(consultLog)

    }

    fun getConsultingLogByPatientId(patient_id : Long) : List<ConsultResponseDto>
    {
        var member = memberRepository.getById(patient_id)

        var page = consultingRepository.findByConsultPatientOrderByDate(member, Pageable.ofSize(10))

        var list = page?.content ?: null

        var result = ArrayList<ConsultResponseDto>()

        if(list==null)
        {
            throw RuntimeException("해당 환자의 상담 기록은 존재하지 않습니다.")
        }

        list.stream().forEach {
            result.add(it.toResponseDto())
        }

        return result
    }

    fun getConsultingLogByPatientEmail(patient_email : String) : List<ConsultResponseDto>
    {
        var member = memberRepository.getByEmail(patient_email)

        var page = consultingRepository.findByConsultPatientOrderByDate(member!!, Pageable.ofSize(10))

        var list = page?.content ?: null

        var result = ArrayList<ConsultResponseDto>()

        if(list==null)
        {
            throw RuntimeException("해당 환자의 상담 기록은 존재하지 않습니다.")
        }

        list.stream().forEach {
            result.add(it.toResponseDto())
        }

        return result
    }

    @Transactional
    fun updateConsultLog(id: Long, consultUpdateDto: ConsultUpdateDto)
    {
        var consultLog = consultingRepository.getById(id)

        consultLog.content = consultUpdateDto.content
        consultLog.date = consultUpdateDto.date

        consultingRepository.save(consultLog)
    }

    fun Consulting.toResponseDto() = ConsultResponseDto(
        consultCounselor.id!!,content,date
    )

}
