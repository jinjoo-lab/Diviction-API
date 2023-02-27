package com.example.diviction.module.diagnosis.dto

data class DiagnosisResultDto(
    var date : String,
    var patientEmail : String,
    var vP1 : Long,
    var vP2 : Long,
    var vP3 : Long
)
