package com.example.diviction.module.drug.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.example.diviction.module.drug.entity.Drug
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DrugRepository : JpaRepository<Drug, Long> {
    override fun findById(id: Long): Optional<Drug>
    fun findByName(name : String) : Optional<Drug>
}