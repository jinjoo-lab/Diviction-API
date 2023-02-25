package com.example.diviction.module.account.repository

import com.example.diviction.module.account.entity.Matching
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MatchRepository : JpaRepository<Matching,Long> {
    override fun findById(id: Long): Optional<Matching>


}
