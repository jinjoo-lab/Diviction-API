package com.example.diviction.module.memo.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class SaveMemoDto(
    var title : String,
    var matchId : Long,
    var content : String,
    )
