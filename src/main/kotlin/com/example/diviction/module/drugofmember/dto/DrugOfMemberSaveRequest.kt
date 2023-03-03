package com.example.diviction.module.drugofmember.dto

import com.example.diviction.module.account.entity.Member
import com.example.diviction.module.drug.entity.Drug
import javax.validation.constraints.NotNull

data class DrugOfMemberSaveRequest (
    @NotNull var member : Member,
    @NotNull var drug : Drug
        ){
}