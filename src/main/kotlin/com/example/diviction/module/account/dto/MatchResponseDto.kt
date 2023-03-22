package com.example.diviction.module.account.dto

import com.example.diviction.module.constant.Gender
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class MatchResponseDto(
    var matchId : Long?,
    var counselorId  : Long?,
    var counselorEmail : String,
    var member : ResponseMemberDto
)
