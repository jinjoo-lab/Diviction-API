package com.example.diviction.module.DAST.repository

import com.example.diviction.module.DAST.entity.Dast
import org.springframework.data.jpa.repository.JpaRepository

interface DastRepository : JpaRepository<Dast,Long>{
    override fun getById(id: Long): Dast
}
