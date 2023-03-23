package com.example.diviction.module.memo.controller

import com.example.diviction.module.memo.dto.ResponseMemoDto
import com.example.diviction.module.memo.dto.SaveMemoDto
import com.example.diviction.module.memo.service.MemoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/memo")
@Tag(name = "MEMO")
class MemoController(
    private val memoService: MemoService
){

    @PostMapping("/save")
    fun saveMemo(@RequestBody saveMemoDto: SaveMemoDto)
    {
        memoService.saveMemo(saveMemoDto)
    }

    @Operation(description = "매칭 Id로 memo list 반환 , 없을 경우 빈 리스트")
    @GetMapping("/get/{matchId}")
    fun getMemoByMatchId(@PathVariable matchId: Long) : MutableList<ResponseMemoDto>
    {
        return memoService.getMemoByMatchId(matchId)
    }


}
