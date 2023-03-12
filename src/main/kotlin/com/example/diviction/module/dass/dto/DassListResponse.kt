package com.example.diviction.module.dass.dto

data class DassListResponse(
        var id: Long,
        var melancholyScore: Int,
        var unrestScore: Int,
        var stressScore: Int,
        var date: String
)
