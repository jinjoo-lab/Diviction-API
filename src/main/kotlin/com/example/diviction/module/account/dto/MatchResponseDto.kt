package com.example.diviction.module.account.dto

data class MatchResponseDto(
    var matchId : Long?,
    var counselorId  : Long?,
    var counselorEmail : String,
    var patientId : Long?,
    var patientEmail : String
)
