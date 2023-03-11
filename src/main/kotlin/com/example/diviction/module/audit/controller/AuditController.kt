package com.example.diviction.module.audit.controller

import com.example.diviction.module.audit.dto.AuditSaveRequest
import com.example.diviction.module.audit.service.AuditService
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/audit")
class AuditController(
        private val auditService: AuditService
) {
    @PostMapping("/save")
    fun saveAudit(
            @RequestBody auditSaveRequest: AuditSaveRequest
    ) {
        auditService.saveAudit(auditSaveRequest)
    }

    @GetMapping("/list/member/{memberId}")
    fun getAuditListByMemberId(
            @PathVariable memberId: Long
    ) = auditService.getAuditListByMemberId(memberId)

    @GetMapping("/list/date/{date}")
    fun getAuditListByDate(
            @PathVariable
            @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
            date: String
    ) = auditService.getAuditListByDate(date)

    @GetMapping("/list/member/{memberId}/date/{date}")
    fun getAuditListByMemberIdAndDate(
            @PathVariable memberId: Long,
            @PathVariable
            @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
            date: String
    ) = auditService.getAuditListByMemberIdAndDate(memberId, date)

    @DeleteMapping("/delete/{id}")
    fun deleteAudit(
            @PathVariable id: Long
    ) {
        auditService.deleteAudit(id)
    }
}