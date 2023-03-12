package com.example.diviction.module.audit.dto

import javax.validation.constraints.NotNull

data class AuditSaveRequest(
        @field: NotNull var memberId: Long,
        @field: NotNull var q1: Int,
        @field: NotNull var score: Int
)
