package com.example.withfuture.service;

import com.example.withfuture.dto.MemberDTO;
import com.example.withfuture.entity.Member;
import com.example.withfuture.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
//    @Autowired
//    MemberService memberService;
//
    @Autowired
    MemberRepository memberRepository ;

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
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SignUp SUCCESS"));
    }



    @Test
    void loginMember() throws Exception {

        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
                .memberId("zan04259@naver.com")
                .password("1234")
                .build();

        String url = "/login";

        String requestBody = objectMapper.writeValueAsString(loginMember);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login Success!!"));

    }


    @Test
    void loginMember2() throws Exception {

        MemberDTO.loginMember loginMember = MemberDTO.loginMember.builder()
                .memberId("zan04259@naver.com")
                .password("1234")
                .build();

        String url = "/login";

        String requestBody = objectMapper.writeValueAsString(loginMember);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("로그인 성공!"));

    }



    @Test
    void logoutMember() throws Exception {

    String url = "/logout";

    mockMvc.perform(MockMvcRequestBuilders.post(url))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("LOGOUT SUCCESS"));
    }
}