package com.example.diviction.module.account.repository

import com.example.diviction.module.account.entity.Counselor
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CounselorRepository : JpaRepository<Counselor,Long> {

    fun existsByEmail(email : String) : Boolean
    override fun findById(id: Long): Optional<Counselor>

    fun findByEmail(email : String) : Optional<Counselor>

    fun getByEmail(email: String) : Counselor?
}
