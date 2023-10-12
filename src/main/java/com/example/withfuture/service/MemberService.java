package com.example.withfuture.service;

import com.example.withfuture.dto.MemberDTO;
import com.example.withfuture.dto.Response;
import com.example.withfuture.entity.Member;
import com.example.withfuture.exception.member.ErrorCode;
import com.example.withfuture.exception.member.MemberException;
import com.example.withfuture.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final Response response;
    private final MemberRepository memberRepository;
    private final HttpServletRequest request;

    @Transactional
    public Member signMember(MemberDTO.memberSignUpDTO memberSign) {
        Member member = Member.registerMember(memberSign);
        return memberRepository.save(member);
    }
    @Transactional
    public HttpSession loginMember(MemberDTO.loginMember loginMember) {
        Member member = memberRepository.findByMemberId(loginMember.getMemberId())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBERID_NULL));

        if (!member.getMember_password().equals(loginMember.getPassword())) {
            throw new MemberException(ErrorCode.LOGIN_PWD_FAILED);
        }

        HttpSession session = request.getSession();
        session.setAttribute("id", member.getMemberId());
        System.out.println("session = " + session.getAttribute("id"));
        return session;
    }

    @Transactional
    public HttpSession logoutMember() {
        HttpSession session = request.getSession();
        if (session.getAttribute("id")==null) {
            throw new MemberException(ErrorCode.SESSION_ID_NULL);
        } else if (session.getAttribute("id") != null) {

            session.invalidate();
            return session;
        } else {
            throw new MemberException(ErrorCode.LOGOUT_FAILED);
        }
    }
}