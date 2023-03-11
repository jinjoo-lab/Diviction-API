package com.example.diviction.module.dass.service

import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.dass.dto.DassSaveRequest
import com.example.diviction.module.dass.entity.Dass
import com.example.diviction.module.dass.repository.DassRepository
import org.springframework.stereotype.Service

@Service
class DassService(
        private val dassRepository: DassRepository,
        private val memberRepository: MemberRepository
) {
    fun getDassList() = dassRepository.findAll()

    fun getDassByMemberId(memberId: Long) = dassRepository.findByMemberId(memberId)

    fun getDassByDate(date: String) = dassRepository.findByDate(date)

    fun getDassByMemberIdAndDate(memberId: Long, date: String) = dassRepository.findByMemberIdAndDate(memberId, date)

    fun getDassDateByMemberId(memberId: Long) = dassRepository.findDateByMemberId(memberId)

    fun saveDass(dassSaveRequest: DassSaveRequest) {
        val dass = Dass()
        dass.apply {
            this.member = memberRepository.findById(dassSaveRequest.memberId).get()
            this.melancholyScore = dassSaveRequest.melancholyScore
            this.unrestScore = dassSaveRequest.unrestScore
            this.stressScore = dassSaveRequest.stressScore
        }
        dassRepository.save(dass)
    }

    fun deleteDass(id: Long) {
        dassRepository.deleteById(id)
    }
}