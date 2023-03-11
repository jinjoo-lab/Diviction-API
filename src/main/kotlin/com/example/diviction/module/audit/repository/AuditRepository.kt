package com.example.diviction.module.audit.repository

import com.example.diviction.module.audit.entity.Audit
import org.springframework.data.jpa.repository.JpaRepository

interface AuditRepository : JpaRepository<Audit, Long> {
    fun findByMemberId(memberId: Long): List<Audit>
    fun findByDate(date: String): List<Audit>
    fun findByMemberIdAndDate(memberId: Long, date: String): List<Audit>
    fun findDateByMemberId(memberId: Long): List<String>
}