package com.example.withfuture.handler;

import com.example.withfuture.dto.Response;
import com.example.withfuture.entity.Member;
import com.example.withfuture.exception.member.ErrorCode;
import com.example.withfuture.exception.member.MemberException;
import com.example.withfuture.service.MemberService;
import com.example.withfuture.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {



    private final MessageService messageService;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (authentication != null) {
            try {
                String successMessage = messageService.getMessage("logout.successMessage");

                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.setStatus(HttpServletResponse.SC_OK);

                // JSON 객체 생성
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("status", 200);
                jsonResponse.put("message", successMessage);

                // JSON 형식으로 응답 보내기
                response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
            } catch (MemberException e) {
                // 에러 처리
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            String failMessage = messageService.getMessage("LOGIN_USER_ERROR");
            // JSON 객체 생성
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("status", 404);
            jsonResponse.put("message", failMessage);

            // JSON 형식으로 응답 보내기
            response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
        }
    }
}
