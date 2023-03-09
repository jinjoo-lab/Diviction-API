package com.example.diviction.security

import com.example.diviction.security.jwt.JwtFilter
import com.example.diviction.security.jwt.TokenProvider
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtSecurityConfiguration(private val tokenProvider: TokenProvider) : SecurityConfigurerAdapter<DefaultSecurityFilterChain?, HttpSecurity>(){
    override fun configure(http: HttpSecurity) {
        val filter  = JwtFilter(tokenProvider)
        http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter::class.java)
    }
}
