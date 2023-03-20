package com.example.diviction.module.DAST.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class GetEmailDastDto(
    val member_email : String,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date : LocalDate
)
