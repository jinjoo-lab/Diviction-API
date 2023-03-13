package com.example.diviction.module.consulting.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class ConsultRequestDto(
    val conuselor_id : Long,
    val patient_id : Long,
    val content : String,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date : LocalDate
)
