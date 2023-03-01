package com.example.diviction.module.checklist.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class SearchCheckList(
    var patientId : Long,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var startDate : LocalDate,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var endDate : LocalDate,
)
