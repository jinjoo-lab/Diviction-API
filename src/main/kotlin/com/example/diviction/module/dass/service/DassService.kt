package com.example.diviction.module.dass.service

import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.dass.dto.DassListResponse
import com.example.diviction.module.dass.dto.DassSaveRequest
import com.example.diviction.module.dass.entity.Dass
import com.example.diviction.module.dass.repository.DassRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class DassService(
        private val dassRepository: DassRepository,
        private val memberRepository: MemberRepository
) {
    fun getDassList() = dassRepository.findAll()

    fun getDassByMemberId(memberId: Long) : List<DassListResponse> {
        return dassRepository.findByMemberId(memberId).map { dass ->
            DassListResponse(
                    id = dass.id!!,
                    melancholyScore = dass.melancholyScore!!,
                    unrestScore = dass.unrestScore!!,
                    stressScore = dass.stressScore!!,
                    date = dass.date!!
            )
        }
    }

    fun getDassByDate(date: String) : List<DassListResponse>{
        return dassRepository.findByDate(date).map { dass ->
            DassListResponse(
                    id = dass.id!!,
                    melancholyScore = dass.melancholyScore!!,
                    unrestScore = dass.unrestScore!!,
                    stressScore = dass.stressScore!!,
                    date = dass.date!!
            )
        }
    }

    fun getDassByMemberIdAndDate(memberId: Long, date: String) : List<DassListResponse> {
        return dassRepository.findByMemberIdAndDate(memberId, date).map { dass ->
            DassListResponse(
                    id = dass.id!!,
                    melancholyScore = dass.melancholyScore!!,
                    unrestScore = dass.unrestScore!!,
                    stressScore = dass.stressScore!!,
                    date = dass.date!!
            )
        }
    }

    fun getDassDateByMemberId(memberId: Long) = dassRepository.findDateByMemberId(memberId)

    fun saveDass(dassSaveRequest: DassSaveRequest) {
        val dass = Dass()
        dass.apply {
            this.member = memberRepository.findById(dassSaveRequest.memberId).get()
            this.melancholyScore = dassSaveRequest.melancholyScore
            this.unrestScore = dassSaveRequest.unrestScore
            this.stressScore = dassSaveRequest.stressScore
            this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }
        dassRepository.save(dass)
    }

    fun deleteDass(id: Long) {
        dassRepository.deleteById(id)
    }
}