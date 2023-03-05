package com.example.diviction.security.repository

import com.example.diviction.security.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository


interface RefreshTokenRepository : JpaRepository<RefreshToken,String> {

}
