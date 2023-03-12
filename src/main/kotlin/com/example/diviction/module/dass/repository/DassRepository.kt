package com.example.diviction.module.dass.repository

import com.example.diviction.module.dass.entity.Dass
import org.springframework.data.jpa.repository.JpaRepository

interface DassRepository: JpaRepository<Dass, Long> {
    fun findByMemberId(memberId: Long): List<Dass>
    fun findByDate(date: String): List<Dass>
    fun findByMemberIdAndDate(memberId: Long, date: String): List<Dass>
    fun findDateByMemberId(memberId: Long): List<String>
}