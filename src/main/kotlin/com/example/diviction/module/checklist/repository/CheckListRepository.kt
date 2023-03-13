package com.example.diviction.module.checklist.repository

import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.checklist.entity.CheckList
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface CheckListRepository : JpaRepository<CheckList,Long> {

    @Query("select c from CheckList c where c.checkPatient= :member and c.startDate >= :start and c.endDate <= :end")
    fun findAllByCheckPatientAndStartDateAndEndDate(@Param("member") patient: Member,@Param("start") startDate: LocalDate, @Param("end") endDate: LocalDate) : List<CheckList>

    @Query("select c from CheckList c where c.checkPatient= :member and c.startDate = :today")
    fun findAllByCheckPatientAndStartDate(@Param("member") patient: Member,@Param("today") date: LocalDate) : List<CheckList>
}
