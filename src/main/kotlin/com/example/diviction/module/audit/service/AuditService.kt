package com.example.diviction.module.audit.service

import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.audit.dto.AuditListResponse
import com.example.diviction.module.audit.dto.AuditSaveRequest
import com.example.diviction.module.audit.entity.Audit
import com.example.diviction.module.audit.repository.AuditRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class AuditService(
        private val auditRepository: AuditRepository,
        private val memberRepository: MemberRepository
) {
    fun getAuditListByMemberId(memberId: Long): List<AuditListResponse> {
        return auditRepository.findByMemberId(memberId).map { audit ->
            AuditListResponse(
                    id = audit.id!!,
                    q1 = audit.q1!!,
                    score = audit.score!!,
                    date = audit.date!!
            )
        }
    }
    fun getAuditListByDate(date: String) : List<AuditListResponse> {
        return auditRepository.findByDate(date).map { audit ->
            AuditListResponse(
                    id = audit.id!!,
                    q1 = audit.q1!!,
                    score = audit.score!!,
                    date = audit.date!!
            )
        }
    }
    fun getAuditListByMemberIdAndDate(
            memberId: Long, date: String
    ) : List<AuditListResponse> {
        return auditRepository.findByMemberIdAndDate(memberId, date).map { audit ->
            AuditListResponse(
                    id = audit.id!!,
                    q1 = audit.q1!!,
                    score = audit.score!!,
                    date = audit.date!!
            )
        }
    }
    fun getDateListByMemberId(memberId: Long) = auditRepository.findDateByMemberId(memberId)
    fun saveAudit(auditSaveRequest: AuditSaveRequest) {
        val audit = Audit().apply {
            this.member = memberRepository.findById(auditSaveRequest.memberId).get()
            this.q1 = auditSaveRequest.q1
            this.score = auditSaveRequest.score
            this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }
        auditRepository.save(audit)
    }
    fun deleteAudit(id: Long) {
        auditRepository.deleteById(id)
    }
}