package com.example.diviction.security.details

import com.example.diviction.module.account.entity.Counselor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CounselorDetails(
    private val counselor: Counselor
) : UserDetails{
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        var roleList : MutableList<GrantedAuthority> = mutableListOf()
        var role = SimpleGrantedAuthority(""+counselor.authority)

        roleList.add(role)

        return roleList
    }

    override fun getPassword(): String {
        return counselor.password
    }

    override fun getUsername(): String {
        return counselor.email
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
