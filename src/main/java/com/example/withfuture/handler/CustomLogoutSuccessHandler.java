package com.example.withfuture.handler;

import com.example.withfuture.dto.Response;
import com.example.withfuture.entity.Member;
import com.example.withfuture.exception.member.ErrorCode;
import com.example.withfuture.exception.member.MemberException;
import com.example.withfuture.service.MemberService;
import com.example.withfuture.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {



    private final MessageService messageService;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if(authentication != null){

            try{
                // 사용자가 로그인한 경우
                // 로그아웃 처리
                String successMessage = messageService.getMessage("logout.successMessage");


                // 성공 메시지를 응답으로 보내기
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK
                response.getWriter().write(successMessage);
            }catch(MemberException e){
                throw new MemberException(ErrorCode.LOGOUT_FAILED);
            }

        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(messageService.getMessage("LOGIN_USER_ERROR"));
        }
    }
}
