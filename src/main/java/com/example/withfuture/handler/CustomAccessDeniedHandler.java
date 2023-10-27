package com.example.withfuture.handler;

import com.example.withfuture.dto.BoardDTO;
import com.example.withfuture.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
    @Autowired
    private BoardService boardService;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        String [] parts = requestURI.split("/");
        String boardIdStr = parts[parts.length - 1];
        Long boardId = Long.parseLong(boardIdStr);


        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

            if(boardService.isAuthorOfBoard(boardId,username)){
                response.getWriter().write("해당 게시글의 수정권한이 없습니다.");
            }

            if(!auth.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") )){
                response.getWriter().write("삭제는 ADMIN만 할 수 있습니다.");
            }
    }
}
