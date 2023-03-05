package com.example.diviction

import com.example.diviction.security.JwtAccessDeniedHandler
import com.example.diviction.security.JwtAuthenticationEntryPoint
import com.example.diviction.security.JwtSecurityConfiguration
import com.example.diviction.security.jwt.JwtFilter
import com.example.diviction.security.jwt.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val tokenProvider: TokenProvider
) : WebSecurityConfigurerAdapter(){
    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .authorizeRequests()
            .antMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .apply(JwtSecurityConfiguration(tokenProvider))
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder
    {
        return BCryptPasswordEncoder()
    }
}
