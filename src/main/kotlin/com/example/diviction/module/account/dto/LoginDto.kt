package com.example.diviction.module.account.dto

import com.example.diviction.security.constants.Authority

data class LoginDto(
    val email : String,
    val password : String,
    val authority: Authority
)
