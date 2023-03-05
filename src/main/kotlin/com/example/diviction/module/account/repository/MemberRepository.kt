package com.example.diviction.module.account.repository

import com.example.diviction.module.account.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member,Long> {

    override fun findById(id: Long): Optional<Member>

    fun findByEmail(email : String) : Optional<Member>

    fun getByEmail(email : String) : Member?
}
