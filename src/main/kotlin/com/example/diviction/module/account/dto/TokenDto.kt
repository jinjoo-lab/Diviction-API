package com.example.diviction.module.account.dto

data class TokenDto (
    val accessToken : String,
    val refreshToken : String,
    private val accessTokenExpiresIn : Long
)
