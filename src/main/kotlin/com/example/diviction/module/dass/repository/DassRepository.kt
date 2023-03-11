package com.example.diviction.module.dass.repository

import com.example.diviction.module.audit.entity.Audit
import com.example.diviction.module.dass.entity.Dass
import org.springframework.data.jpa.repository.JpaRepository

interface DassRepository: JpaRepository<Dass, Long> {
    fun findByMemberId(memberId: Long): List<Audit>
    fun findByDate(date: String): List<Audit>
    fun findByMemberIdAndDate(memberId: Long, date: String): List<Audit>
    fun findDateByMemberId(memberId: Long): List<String>
}