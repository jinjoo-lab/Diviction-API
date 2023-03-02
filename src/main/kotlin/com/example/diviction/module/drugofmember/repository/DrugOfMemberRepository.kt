package com.example.diviction.module.drugofmember.repository

import com.example.diviction.module.drugofmember.entity.DrugOfMember
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DrugOfMemberRepository : JpaRepository<DrugOfMember, Long> {
    fun findByMemberId(memberId : Long) : List<DrugOfMember>
    fun findByDrugId(drugId : Long) : List<DrugOfMember>
    fun findByMemberIdAndDrugId(memberId : Long, drugId : Long) : Optional<DrugOfMember>
    override fun findById(id: Long): Optional<DrugOfMember>

}