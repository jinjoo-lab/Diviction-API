package com.example.diviction.security.details

import com.example.diviction.module.account.entity.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MemberDetails(
    private val member: Member
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        var roleList : MutableList<GrantedAuthority> = mutableListOf()
        var role = SimpleGrantedAuthority(member.authority.toString())

        roleList.add(role)

        return roleList
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}
