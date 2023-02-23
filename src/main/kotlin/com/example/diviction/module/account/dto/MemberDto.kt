package com.example.diviction.module.account.dto

import com.example.diviction.module.constant.Gender
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class MemberDto(
    @Email
    var email : String,

    var password : String,

    var name : String,

    var birth : String,

    var address : String,

    var gender : Gender,

    var profile_img_url : String
)
