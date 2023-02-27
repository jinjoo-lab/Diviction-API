package com.example.diviction.module.consulting.repository

import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.consulting.entity.Consulting
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ConsultingRepository : JpaRepository<Consulting, Long> {

    fun findByConsultPatientOrderByDate(patient : Member, pageable: Pageable) : Page<Consulting>?
}
