package com.example.diviction.module.account.dto

import com.example.diviction.module.constant.Gender
import org.springframework.format.annotation.DateTimeFormat
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class MemberDto(
    var email : String,

    var password : String,

    var name : String,

    var birth : String,

    var address : String,

    var gender : Gender,

    var profile_img_url : String
)
