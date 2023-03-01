package com.example.diviction.module.checklist.repository

import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.checklist.entity.CheckList
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface CheckListRepository : JpaRepository<CheckList,Long> {
    fun findAllByCheckPatientAndStartDateAndEndDate(patient : Member,startDate : LocalDate,endDate: LocalDate) : List<CheckList>
}
