package com.example.diviction.module.memo.repository

import com.example.diviction.module.account.entity.Matching
import com.example.diviction.module.memo.entity.Memo
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MemoRepository : JpaRepository<Memo,Long> {
    override fun getById(id: Long): Memo

    fun getAllByMatch(match : Matching, pageable: Pageable) : List<Memo>

}
