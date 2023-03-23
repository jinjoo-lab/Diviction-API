package com.example.diviction.module.memo.controller

import com.example.diviction.module.memo.dto.RequestMemoDto
import com.example.diviction.module.memo.service.MemoService
import io.swagger.v3.oas.annotations.tags.Tag
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
    fun saveMemo(@RequestBody requestMemoDto: RequestMemoDto)
    {
        memoService.saveMemo(requestMemoDto)
    }


}
