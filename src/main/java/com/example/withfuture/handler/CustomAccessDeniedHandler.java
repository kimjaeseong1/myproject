package com.example.withfuture.handler;

import com.example.withfuture.exception.board.BoardErrorCode;
import com.example.withfuture.exception.board.BoardException;
import com.example.withfuture.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final MessageService messageService;


    public CustomAccessDeniedHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("auth"+auth.getName());

            if(!auth.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") )){
                System.out.println("삭제 조건 구문에 들어옴");
                request.setAttribute("message",messageService.getMessage("CUSTOM_DELETE_ERROR"));
                request.setAttribute("nextPage","/view/board/list");

            }else{
                request.setAttribute("message",messageService.getMessage("MUST_LOGIN_ERROR"));
                request.setAttribute("nextPage","/view/login");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                SecurityContextHolder.clearContext();
            }

        request.getRequestDispatcher("/denied-page").forward(request,response);
     }
    }

