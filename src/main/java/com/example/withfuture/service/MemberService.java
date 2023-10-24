package com.example.withfuture.service;

import com.example.withfuture.dto.MemberDTO;
import com.example.withfuture.entity.Member;
import com.example.withfuture.exception.member.ErrorCode;
import com.example.withfuture.exception.member.MemberException;
import com.example.withfuture.repository.MemberRepository;
import com.example.withfuture.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpServletRequest request;


    @Transactional
    public Member signMember(MemberDTO.memberSignUpDTO memberSign) {
        Member member = Member.registerMember(memberSign);
        return memberRepository.save(member);
    }

    @Transactional
    public void loginMember(MemberDTO.loginMember loginMember) {
        Member member = memberRepository.findByMemberId(loginMember.getMemberId())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBERID_NULL,"memberId"));

        if (!member.getMember_password().equals(loginMember.getPassword())) {
            throw new MemberException(ErrorCode.LOGIN_PWD_FAILED,"password");
        }
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getMember_password(),authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);

    }

//    @Transactional
//    public void logoutMember() {
//        String currentUsername = SecurityUtils.getCurrentUsername();
//        if(currentUsername == null){
//            throw new MemberException(ErrorCode.LOGOUT_FAILED);
//        }
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new CustomUserDetails(member);
    }
}