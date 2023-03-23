package com.example.diviction.module.memo.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class SaveMemoDto(
    var title : String,
    var matchId : Long,
    var content : String,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val initDate : LocalDateTime,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val modiDate : LocalDateTime,
    )
