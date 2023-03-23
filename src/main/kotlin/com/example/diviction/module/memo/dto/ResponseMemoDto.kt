package com.example.diviction.module.memo.dto

import com.example.diviction.module.account.dto.MatchDto
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ResponseMemoDto(
    val memoId : Long,
    var title : String,
    var content : String,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val initDate : LocalDateTime,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val modiDate : LocalDateTime
    )
