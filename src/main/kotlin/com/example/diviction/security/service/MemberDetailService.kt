package com.example.diviction.security.service

import com.example.diviction.module.account.repository.CounselorRepository
import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.security.details.MemberDetails
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class MemberDetailService(
    private val memberRepository: MemberRepository,
    private val counselorRepository: CounselorRepository
) : UserDetailsService
{
    override fun loadUserByUsername(username: String?): UserDetails {
        val member = memberRepository.getByEmail(username!!) ?: throw RuntimeException("User not found")
        return MemberDetails(member)
    }

}

