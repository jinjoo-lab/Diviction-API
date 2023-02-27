package com.example.diviction.module.diagnosis.dto

import org.springframework.format.annotation.DateTimeFormat
import javax.validation.constraints.Email

data class DiagnosisResultDto(
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var date : String,
    @Email
    var patientEmail : String,
    var vP1 : Long,
    var vP2 : Long,
    var vP3 : Long
)
