package com.example.diviction.module.checklist.dto

import com.example.diviction.module.constant.CheckListState
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class ResultCheckList(
    var checkListId : Long?,
    var patientId : Long?,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var startDate : LocalDate,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var endDate : LocalDate,
    var content : String,
    var state : CheckListState
)
