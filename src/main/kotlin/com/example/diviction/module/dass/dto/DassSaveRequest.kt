package com.example.diviction.module.dass.dto

data class DassSaveRequest(
        var memberId: Long,
        var melancholyScore: Int,
        var unrestScore: Int,
        var stressScore: Int
)
