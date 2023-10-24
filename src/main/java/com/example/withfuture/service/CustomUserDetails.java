package com.example.withfuture.service;

import com.example.withfuture.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;


public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends  GrantedAuthority> authorities;

    public CustomUserDetails(Member member){
        this.username = member.getMemberId();
        this.password = member.getMember_password();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
    }





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
