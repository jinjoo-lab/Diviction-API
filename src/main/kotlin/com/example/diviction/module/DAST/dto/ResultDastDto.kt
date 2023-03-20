package com.example.diviction.module.DAST.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.validation.constraints.NotBlank

data class ResultDastDto(
    val id : Long,

    val drug : String,

    val date : LocalDate,

    val frequency : Long,

    val injection : Long,

    val cure : Long,

    val question : Long
)

