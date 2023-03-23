package com.example.diviction.module.memo.service

import com.example.diviction.module.account.repository.MatchRepository
import com.example.diviction.module.memo.dto.RequestMemoDto
import com.example.diviction.module.memo.entity.Memo
import com.example.diviction.module.memo.repository.MemoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemoService(
    private val memoRepository: MemoRepository,
    private val matchRepository: MatchRepository
) {

    @Transactional
    fun saveMemo(requestMemoDto: RequestMemoDto)
    {
        val match = matchRepository.getById(requestMemoDto.matchId)
        val memo = Memo(
            requestMemoDto.title,requestMemoDto.contemt,requestMemoDto.initDate,
            requestMemoDto.modiDate,match)
        memoRepository.save(memo)
        match.memoList.add(memo)
    }

}
