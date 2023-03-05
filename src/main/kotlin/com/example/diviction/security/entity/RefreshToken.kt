package com.example.diviction.security.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="refresh_token")
class RefreshToken (
        @Id
        @Column(name="token_key")
        val tokenKey : String,
        @Column(name="token_value")
        val tokenValue : String)
