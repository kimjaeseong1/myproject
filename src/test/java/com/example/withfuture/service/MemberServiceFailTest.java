package com.example.withfuture.service;

import com.example.withfuture.dto.MemberDTO;
import com.example.withfuture.entity.Member;
import com.example.withfuture.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ConstraintViolation;

import java.util.Set;

import static com.example.withfuture.exception.member.ErrorCode.SIGNUP_FAILED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberServiceFailTest {
    @Autowired
    MockMvc mockMvc;

//    @Autowired
//    private LocalValidatorFactoryBean validator;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository ;


//    @DisplayName("이메일 형식으로 id 입력하지 않으면 회원가입 실패")
//    @Transactional
//    @Test
//    void signMember() throws Exception {
//        MemberDTO.memberSignUpDTO request = MemberDTO.memberSignUpDTO.builder()
//                .memberId("rlawotjdl")
//                .password("1234")
//                .memberName("tsetset")
//                .build();
//
//        Member member = Member.registerMember(request);
//
//        String url = "/signup";
//        String requestBody = objectMapper.writeValueAsString(member);
//        mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("ValidationError")) // JSON 응답에서 "code" 필드 검증
//                .andExpect(jsonPath("$.errors[0].field").value("memberId")) // JSON 응답에서 "errors[0].field" 필드 검증
//                .andExpect(jsonPath("$.errors[0].message").value("올바른 형식의 이메일 주소여야 합니다"));
//    }
//
//    @DisplayName("비밀번호 형식에 맞춰 입력하지 않으면 실패")
//    @Transactional
//    @Test
//    void signMemberPwd() throws Exception {
//        MemberDTO.memberSignUpDTO request = MemberDTO.memberSignUpDTO.builder()
//                .memberId("test123@naver.com")
//                .password("invalid")
//                .memberName("testtest")
//                .build();
//
//        //Member member = Member.registerMember(request);
//
//        String url = "/signup";
//
//        String requestBody = objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType("application/json")
//                        .content(requestBody))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("ValidationError"))
//                .andExpect(jsonPath("$.errors[0].field").value("password"))
//                .andExpect(jsonPath("$.errors[0].message").value("영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다."));
//    }
//
//
//
//
//    @DisplayName("비밀번호 형식에 맞춰 입력하지 않으면 실패")
//    @Transactional
//    @Test
//    void signMemberPwdUS() throws Exception {
//
//        MemberDTO.memberSignUpDTO request = MemberDTO.memberSignUpDTO.builder()
//                .memberId("test123@naver.com")
//                .memberName("testtest")
//                .password("invalid")
//                .build();
//
//       // Member member = Member.registerMember(request);
//
//        String url = "/signup";
//
//        String requestBody = objectMapper.writeValueAsString(request);
//        System.out.println("request="+requestBody);
//        mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("ValidationError"))
//                .andExpect(jsonPath("$.errors[0].field").value("password"))
//                .andExpect(jsonPath("$.errors[0].message").value("It must contain at least 8 characters, including letters, numbers, and special characters."));
//    }
//
//    @DisplayName("회원 닉네임 20자 이상 입력하면 회원가입 실패 ")
//    @Transactional
//    @Test
//    void signMemberNick() throws Exception {
//        MemberDTO.memberSignUpDTO request = MemberDTO.memberSignUpDTO.builder()
//                .memberId("test@naver.com")
//                .password("test123!")
//                .memberName("wekjrhkusdhfkueshfksuehfkushdkjfheuh")
//                .build();
//
//        Member member = Member.registerMember(request);
//
//        String url = "/signup";
//
//        String requestBody = objectMapper.writeValueAsString(member);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType("application/json")
//                        .content(requestBody))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("ValidationError")) // JSON 응답에서 "code" 필드 검증
//                .andExpect(jsonPath("$.errors[0].field").value("memberName")) // JSON 응답에서 "errors[0].field" 필드 검증
//                .andExpect(jsonPath("$.errors[0].message").value("20자 이상 입력 할 수 없습니다."));
//    }
//
//    @DisplayName("Password 입력하지 않으면 회원가입 실패 ")
//    @Test
//    @Transactional
//    void signMemberNotPwd() throws Exception {
//        MemberDTO.memberSignUpDTO request = MemberDTO.memberSignUpDTO.builder()
//                .memberId("tirjeij@naver.com")
//                .memberName("wekj")
//                .build();
//
//        Member member = Member.registerMember(request);
//
//        String url = "/signup";
//
//        String requestBody = objectMapper.writeValueAsString(member);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType("application/json")
//                        .content(requestBody))
//                .andExpect(status().isBadRequest());
//    }
//
//
//
//
//    @DisplayName("아이디 다르게 입력하면 로그인 실패")
//    @Test
//    void InvalidLogin() throws Exception {
//        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
//                .memberId("zan04ewq@naver.com")
//                .password("1234")
//                .build();
//
//        String url = "/login";
//
//        String requestBody = objectMapper.writeValueAsString(loginMember);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType("application/json")
//                        .content(requestBody))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @DisplayName("아이디를 입력하지 않으면 실패 한국어")
//    @Test
//    void InvalidLoginKO() throws Exception {
//        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
//                .password("1234")
//                .build();
//
//        String url = "/login";
//
//        String requestBody = objectMapper.writeValueAsString(loginMember);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType("application/json")
//                        .content(requestBody))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("ValidationError"))
//                .andExpect(jsonPath("$.errors[0].field").value("memberId"))
//                .andExpect(jsonPath("$.errors[0].message").value("필수 입력 사항입니다."));
//    }
//@DisplayName(" 비밀번호 입력하지 않으면 실패 한국어")
//    @Test
//    void loginMemberPwdKO() throws Exception {
//        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
//                .memberId("zan04259@naver.com") //디비에 저장된 아이디 : zan04259@naver.com
//                .build();
//
//        String url = "/login";
//
//        String requestBody = objectMapper.writeValueAsString(loginMember);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType("application/json")
//                        .content(requestBody))
//                .andExpect(status().isUnauthorized())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("ValidationError"))
//                .andExpect(jsonPath("$.errors[0].field").value("password"))
//                .andExpect(jsonPath("$.errors[0].message").value("비밀번호는 필수 입력 사항입니다."));
//    }


    @DisplayName("아이디 다르게 입력하면 로그인 실패 영어")
    @Test
    void InvalidLogin2() throws Exception {
        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
                .memberId("zan04ewq@naver.com")
                .password("fhsk7171!")
                .build();

        String url = "/login";

        String requestBody = objectMapper.writeValueAsString(loginMember);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MEMBERID_NULL"))
                .andExpect(jsonPath("$.errors[0].field").value("memberId"))
                .andExpect(jsonPath("$.errors[0].message").value("memberId Null"));
    }

    @DisplayName("이메일 형식으로 id 입력하지 않으면 회원가입 실패(영어)")
    @Transactional
    @Test
    void signMember2() throws Exception {
        MemberDTO.memberSignUpDTO request = MemberDTO.memberSignUpDTO.builder()
                .memberId("rlawotjdl")
                .password("1234fhdksl1!")
                .memberName("tsetset")
                .build();

        Member member = Member.registerMember(request);
        System.out.println("requestBody =" + member.getMemberId());
        System.out.println("requestBody =" + member.getMember_password());
        System.out.println("requestBody =" + member.getMemberName());
        String url = "/signup";
        String requestBody = objectMapper.writeValueAsString(member);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ValidationError"))
                .andExpect(jsonPath("$.errors[0].field").value("memberId"))
                .andExpect(jsonPath("$.errors[0].message").value("must be a well-formed email address"));
    }



    @DisplayName("회원 닉네임 20자 이상 입력하면 회원가입 실패(영어) ")
    @Transactional
    @Test
    void signMemberNick2() throws Exception {
        MemberDTO.memberSignUpDTO request = MemberDTO.memberSignUpDTO.builder()
                .memberId("test@naver.com")
                .password("test123!")
                .memberName("wekjrhkusdhfkueshfksuehfkushdkjfheuh")
                .build();

        Member member = Member.registerMember(request);

        String url = "/signup";

        String requestBody = objectMapper.writeValueAsString(member);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ValidationError"))
                .andExpect(jsonPath("$.errors[0].field").value("memberName"))
                .andExpect(jsonPath("$.errors[0].message").value("You cannot enter more than 20 characters."));
    }

    @DisplayName("회원Id 입력하지 않으면 회원가입 실패(영어)")
    @Test
    @Transactional
    void signMemberNotId() throws Exception {
        MemberDTO.memberSignUpDTO request = MemberDTO.memberSignUpDTO.builder()
                .password("test123!")
                .memberName("wekj")
                .build();

        Member member = Member.registerMember(request);

        String url = "/signup";

        String requestBody = objectMapper.writeValueAsString(member);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ValidationError"))
                .andExpect(jsonPath("$.errors[0].field").value("memberId"))
                .andExpect(jsonPath("$.errors[0].message").value("This field must be entered."));
    }





    @DisplayName("아이디 입력하지 않으면 실패 영어")
    @Test
    void InvalidLoginUS() throws Exception {
        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
                .password("1234")
                .build();

        String url = "/login";

        String requestBody = objectMapper.writeValueAsString(loginMember);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ValidationError"))
                .andExpect(jsonPath("$.errors[0].field").value("memberId"))
                .andExpect(jsonPath("$.errors[0].message").value("This field must be entered."));
    }

    @DisplayName(" 비밀번호 다르게 입력하면 로그인 실패 영어")
    @Test
    void loginMemberPwd() throws Exception {
        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
                .memberId("zan04259@naver.com") //디비에 저장된 아이디 : zan04259@naver.com
                .password("1111") // 해당 아이디에 저장된 비밀번호 : 1234
                .build();

        String url = "/login";

        String requestBody = objectMapper.writeValueAsString(loginMember);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("LOGIN_PWD_FAILED"))
                .andExpect(jsonPath("$.errors[0].field").value("password"))
                .andExpect(jsonPath("$.errors[0].message").value("Passwords do not match."));

    }

    @DisplayName("세션에 아이디가 없을 경우 로그아웃 실패 (영어)")
    @Test
    void logoutMember() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id",null);
        String url = "/logout";

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .session(session))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("SESSION_ID_NULL"))
                .andExpect(jsonPath("$.errors[0].message").value("Session ID is null!"));

     }

    @DisplayName(" 비밀번호 입력하지 않으면 실패 영어")
    @Test
    void loginMemberPwdUS() throws Exception {
        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
                .memberId("zan04259@naver.com") //디비에 저장된 아이디 : zan04259@naver.com
                .build();

        String url = "/login";

        String requestBody = objectMapper.writeValueAsString(loginMember);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ValidationError"))
                .andExpect(jsonPath("$.errors[0].field").value("password"))
                .andExpect(jsonPath("$.errors[0].message").value("Password is must be entered."));
    }
}
