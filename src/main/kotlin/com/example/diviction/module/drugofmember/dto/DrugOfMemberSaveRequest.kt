package com.example.diviction.module.drugofmember.dto

import javax.validation.constraints.NotNull

data class DrugOfMemberSaveRequest (
    @field: NotNull val memberId : Long,
    @field: NotNull val drugId : Long
        ){
}