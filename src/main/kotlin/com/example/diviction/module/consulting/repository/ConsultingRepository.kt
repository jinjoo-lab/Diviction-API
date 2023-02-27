package com.example.diviction.module.consulting.repository

import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.consulting.entity.Consulting
import org.springframework.data.jpa.repository.JpaRepository

interface ConsultingRepository : JpaRepository<Consulting, Long> {

}
