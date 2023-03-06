package com.example.diviction.security.service

import com.example.diviction.module.account.repository.CounselorRepository
import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.security.details.CounselorDetails
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
        if(username==null)
        {
            throw RuntimeException("Username cannot be null!!")
        }

        else{
            if(memberRepository.existsByEmail(username))
            {
                val member = memberRepository.getByEmail(username)
                return MemberDetails(member!!)
            }
            else if(counselorRepository.existsByEmail(username))
            {
                val counselor = counselorRepository.getByEmail(username)
                return CounselorDetails(counselor!!)
            }

            throw RuntimeException("User Not Found")
        }
    }

}

