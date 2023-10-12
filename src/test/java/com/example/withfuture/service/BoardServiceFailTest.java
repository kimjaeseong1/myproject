package com.example.withfuture.service;

import com.example.withfuture.dto.BoardDTO;
import com.example.withfuture.entity.Board;
import com.example.withfuture.repository.BoardRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BoardServiceFailTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    BoardRepository boardRepository;


    MockHttpSession session = new MockHttpSession();

    @DisplayName("제목을 입력하지 않으면 게시글 작성 실패")
    @Transactional
    @Test
    void registerBoardLessTitle() throws Exception {
        //MockHttpSession session = new MockHttpSession();

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"memberId\":\"zan04259@naver.com\", \"password\":\"1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("LOGIN SUCCESS"));
        session.setAttribute("id","zan04259@naver.com");

        BoardDTO.BoardReqDTO boardDTO = BoardDTO.BoardReqDTO.builder()
                .content("ref APi 호출 테스트")
                .build();

        Board board = Board.registerBoard(boardDTO);

        String url = "/board";

        String requestBody = objectMapper.writeValueAsString(board);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody)
                        .session(session))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("제목을 입력하지 않으면 게시글 작성 실패")
    @Transactional
    @Test
    void registerBoardLessContent() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"memberId\":\"zan04259@naver.com\", \"password\":\"1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("LOGIN SUCCESS"));
        session.setAttribute("id","zan04259@naver.com");

        BoardDTO.BoardReqDTO boardDTO = BoardDTO.BoardReqDTO.builder()
                .title("resr")
                .build();

        Board board = Board.registerBoard(boardDTO);

        String url = "/board";

        String requestBody = objectMapper.writeValueAsString(board);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody)
                        .session(session))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("로그인 하지 않으면 게시글 작성 실패")
    @Transactional
    @Test
    void registerBoardNotUser() throws Exception {
        BoardDTO.BoardReqDTO boardDTO = BoardDTO.BoardReqDTO.builder()
                .title("resr")
                .content("fsefdf")
                .build();

        Board board = Board.registerBoard(boardDTO);

        String url = "/board";

        String requestBody = objectMapper.writeValueAsString(board);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody)
                        .session(session))
                .andExpect(status().is5xxServerError());
    }

    @DisplayName("BoardId를 입력하지 않으면 udpate 실패")
    @Transactional
    @Test
    void updateBoard() throws Exception {
        BoardDTO.updateBoardDTO updateBoard = new BoardDTO.updateBoardDTO("wer","wer");


        String requestBody = objectMapper.writeValueAsString(updateBoard);

        mockMvc.perform(put("/board/{boardId}", 36)
                        .contentType(MediaType. APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError());


    }

    @DisplayName("boardId 없으면 삭제 실패")
    @Transactional
    @Test
    void deleteBoard() throws Exception {
        long nonExistentBoardId = 250; // 존재하지 않는 게시물 ID를 사용

        String url = "/board/" + nonExistentBoardId;

        mockMvc.perform(MockMvcRequestBuilders.delete(url))
                .andExpect(status().isNotFound()) // 상태 코드 404 Not Found를 기대
                .andExpect(content().json("{\"state\":\"404 NOT_FOUND\",\"message\":\"BoardId Null\"}"));
    }

    @Test
    void boardInquiry() {
    }

    @Test
    void boardTitleSearch() {
    }

    @Test
    void boardDetailInquiry() {
    }
}