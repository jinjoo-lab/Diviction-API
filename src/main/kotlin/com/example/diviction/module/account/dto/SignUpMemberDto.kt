package com.example.diviction.module.account.dto

import com.example.diviction.module.constant.Gender
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class SignUpMemberDto(
    var id : Long,

    var email : String,

    var password : String,

    var name : String,

    var birth : String,

    var address : String,

    @Enumerated(EnumType.STRING)
    var gender : Gender,

    var profile_img_url : String
)
