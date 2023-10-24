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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    MockHttpSession session = new MockHttpSession();
//    @Autowired
//    MemberService memberService;

    @Autowired
    MemberRepository memberRepository ;


    @DisplayName("회원가입 성공 케이스")
    @Transactional
    @Test
    void signMember() throws Exception {

        MemberDTO.memberSignUpDTO request = MemberDTO.memberSignUpDTO.builder()
                .memberId("rlawotjd@naver.com")
                .password("fhsk7171!")
                .memberName("JunitTest")
                .build();

        Member member = Member.registerMember(request);

        String url = "/signup";

        String requestBody = objectMapper.writeValueAsString(member);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("SignUp Success!!"));
    }



    @DisplayName("로그인 성공 케이스")
    @Test
    @Transactional
    void loginMember() throws Exception {

        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
                .memberId("aaa@naver.com")
                .password("aaaa1234!")
                .build();

        String url = "/login";

        String requestBody = objectMapper.writeValueAsString(loginMember);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"state\":200,\"result\":\"SUCCESS\",\"message\":\"Login Success!!\",\"data\":null}"));

    }

    @WithMockUser
    @DisplayName("로그아웃 성공 케이스")
    @Test
    void logoutMember() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"memberId\":\"aaa@naver.com\", \"password\":\"aaaa1234!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login Success!!"));

    String url = "/userlogout";

    mockMvc.perform(MockMvcRequestBuilders.post(url)
            .with(csrf())
            .contentType("application/json"))
            .andExpect(status().isOk());
    }


    @DisplayName("아이디 다르게 입력하면 로그인 실패 영어")
    @Test
    void InvalidLogin2() throws Exception {
        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
                .memberId("cccc@naver.com")
                .password("cccc1234!")
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
                .memberId("aaa@naver.com") //디비에 저장된 아이디 : zan04259@naver.com
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

    @DisplayName("로그인 사용자가 없을 때 로그아웃 실패 (영어)")
    @Test
    void logoutMemberFail() throws Exception {


        String url = "/logout";

        mockMvc.perform(MockMvcRequestBuilders.post(url))
                .andExpect(status().isNotFound());

    }

    @DisplayName(" 비밀번호 입력하지 않으면 실패 영어")
    @Test
    void loginMemberPwdUS() throws Exception {
        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
                .memberId("ccc@naver.com") //디비에 저장된 아이디 : zan04259@naver.com
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