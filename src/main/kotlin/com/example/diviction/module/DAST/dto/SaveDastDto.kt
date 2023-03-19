package com.example.diviction.module.DAST.dto

import com.example.diviction.module.DAST.constant.DASTDRUG
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotBlank


data class SaveDastDto(
    @Enumerated(value = EnumType.STRING)
    val drug: List<DASTDRUG>,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,

    @field: NotBlank
    val member_email : String,

    val frequency: Long,

    val injection: Long,

    val cure: Long,

    val question: Long
)
