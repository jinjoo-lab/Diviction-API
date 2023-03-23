package com.example.diviction.module.memo.service

import com.example.diviction.module.account.repository.MatchRepository
import com.example.diviction.module.memo.dto.ResponseMemoDto
import com.example.diviction.module.memo.dto.SaveMemoDto
import com.example.diviction.module.memo.entity.Memo
import com.example.diviction.module.memo.repository.MemoRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemoService(
    private val memoRepository: MemoRepository,
    private val matchRepository: MatchRepository
) {

    @Transactional
    fun saveMemo(saveMemoDto: SaveMemoDto)
    {
        val match = matchRepository.getById(saveMemoDto.matchId)
        val memo = Memo(
            saveMemoDto.title,saveMemoDto.content,saveMemoDto.initDate,
            saveMemoDto.modiDate,match)
        memoRepository.save(memo)
        match.memoList.add(memo)
    }

    fun getMemoByMatchId(id : Long) : MutableList<ResponseMemoDto>
    {
        val match = matchRepository.getById(id)
        val list : MutableList<ResponseMemoDto> = mutableListOf()

        memoRepository.getAllByMatch(match, Pageable.ofSize(15)).forEach {
            list.add(it.toResponseDto())
        }

        return list
    }

    fun Memo.toResponseDto() = ResponseMemoDto(
        id!!,title,content,initDate, modiDate
    )
}
