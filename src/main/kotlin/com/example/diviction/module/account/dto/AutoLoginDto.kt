package com.example.diviction.module.account.dto

import com.example.diviction.security.constants.Authority

data class AutoLoginDto(
    val accessToken : String,
    val refreshToken : String,
    val authority: Authority
)
