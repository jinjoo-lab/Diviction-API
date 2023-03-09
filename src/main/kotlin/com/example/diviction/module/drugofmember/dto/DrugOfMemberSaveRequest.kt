package com.example.diviction.module.drugofmember.dto

import javax.validation.constraints.NotBlank

data class DrugOfMemberSaveRequest (
    @NotBlank val memberId : Long,
    @NotBlank val drugId : Long
        ){
}