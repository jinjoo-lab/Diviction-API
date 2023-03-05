package com.example.diviction.security.service

import com.example.diviction.module.account.repository.CounselorRepository
import com.example.diviction.security.details.CounselorDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CounselorDetailService(private val counselorRepository: CounselorRepository) : UserDetailsService{
    override fun loadUserByUsername(username: String?): UserDetails {
        val counselor = counselorRepository.getByEmail(username!!) ?: throw RuntimeException("Counselor not found")
        return CounselorDetails(counselor)
    }

}
