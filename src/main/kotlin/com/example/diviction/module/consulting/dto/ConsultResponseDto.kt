package com.example.diviction.module.consulting.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class ConsultResponseDto(
    val counselorId : Long,
    val patientId : Long,
    val content : String,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date : LocalDate
)
