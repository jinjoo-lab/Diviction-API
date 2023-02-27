package com.example.diviction.module.consulting.dto

data class ConsultRequestDto(
    val conuselor_id : Long,
    val patient_id : Long,
    val content : String,
    val date : String
)
