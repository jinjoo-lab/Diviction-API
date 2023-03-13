package com.example.diviction.module.audit.controller

import com.example.diviction.module.audit.dto.AuditSaveRequest
import com.example.diviction.module.audit.service.AuditService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/audit")
class AuditController(
        private val auditService: AuditService
) {
    @Operation(description = "Audit 자가 진단 정보 저장")
    @PostMapping("/save")
    fun saveAudit(
            @RequestBody auditSaveRequest: AuditSaveRequest
    ) {
        auditService.saveAudit(auditSaveRequest)
    }

    @Operation(description = "특정 회원의 Audit 자가 진단 정보 다건 조회")
    @GetMapping("/list/member/{memberId}")
    fun getAuditListByMemberId(
            @PathVariable memberId: Long
    ) = auditService.getAuditListByMemberId(memberId)

    @Operation(description = "특정 날짜의 Audit 자가 진단 정보 다건 조회")
    @GetMapping("/list/date/{date}")
    fun getAuditListByDate(
            @PathVariable
            @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
            date: String
    ) = auditService.getAuditListByDate(date)

    @Operation(description = "특정 회원의 특정 날짜의 Audit 자가 진단 정보 다건 조회")
    @GetMapping("/list/member/{memberId}/date/{date}")
    fun getAuditListByMemberIdAndDate(
            @PathVariable memberId: Long,
            @PathVariable
            @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
            date: String
    ) = auditService.getAuditListByMemberIdAndDate(memberId, date)

    @Operation(description = "Audit Id로 특정 Audit 자가 진단 정보 단건 삭제")
    @DeleteMapping("/delete/{id}")
    fun deleteAudit(
            @PathVariable id: Long
    ) {
        auditService.deleteAudit(id)
    }
}