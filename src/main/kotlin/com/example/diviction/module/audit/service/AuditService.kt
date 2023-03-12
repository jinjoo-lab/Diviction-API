package com.example.diviction.module.audit.service

import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.audit.dto.AuditSaveRequest
import com.example.diviction.module.audit.entity.Audit
import com.example.diviction.module.audit.repository.AuditRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuditService(
        private val auditRepository: AuditRepository,
        private val memberRepository: MemberRepository
) {
    fun getAuditListByMemberId(memberId: Long) = auditRepository.findByMemberId(memberId)
    fun getAuditListByDate(date: String) = auditRepository.findByDate(date)
    fun getAuditListByMemberIdAndDate(memberId: Long, date: String) = auditRepository.findByMemberIdAndDate(memberId, date)
    fun getDateListByMemberId(memberId: Long) = auditRepository.findDateByMemberId(memberId)
    fun saveAudit(auditSaveRequest: AuditSaveRequest) {
        var audit = Audit().apply {
            this.member = memberRepository.findById(auditSaveRequest.memberId!!).get()
            this.q1 = auditSaveRequest.q1
            this.score = auditSaveRequest.score
            this.date = LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
        }
        auditRepository.save(audit)
    }
    fun deleteAudit(id: Long) {
        auditRepository.deleteById(id)
    }
}