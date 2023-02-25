package com.example.diviction.module.account.dto

import com.example.diviction.module.constant.Gender
import javax.validation.constraints.Email

data class CounselorDto (
    @Email
    var email : String,

    var password : String,

    var name : String,

    var birth : String,

    var address : String,

    var gender : Gender,

    var profile_img_url : String,

    var confirm : Boolean
)
