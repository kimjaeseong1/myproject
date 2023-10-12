package com.example.withfuture.service;

import com.example.withfuture.dto.BoardDTO;
import com.example.withfuture.entity.Board;
import com.example.withfuture.repository.BoardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BoardServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    BoardRepository boardRepository;

    @Transactional
    @Test
    void registerBoard() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"memberId\":\"zan04259@naver.com\", \"password\":\"1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("LOGIN SUCCESS"));
        session.setAttribute("id","zan04259@naver.com");

        BoardDTO.BoardReqDTO boardDTO = BoardDTO.BoardReqDTO.builder()
                .title("refactoring")
                .content("ref APi 호출 테스트")
                .build();

        Board board = Board.registerBoard(boardDTO);

        String url = "/board";

        String requestBody = objectMapper.writeValueAsString(board);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType("application/json")
                .content(requestBody)
                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("게시글 등록 성공"));

    }

    @Transactional
    @Test
    void updateBoard() throws Exception {

        BoardDTO.updateBoardDTO updateBoardDTO = new BoardDTO.updateBoardDTO(36,"api호출","update Test");

        Board board = boardRepository.findByBoardId(updateBoardDTO.getBoardId()).get();

        board.updateBoard(updateBoardDTO.getTitle(), updateBoardDTO.getContent());

        String requestBody  = objectMapper.writeValueAsString(board);

        mockMvc.perform(put("/board/{boardId}", 36)
                .contentType(MediaType. APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

    }

    @Transactional
    @Test
    void deleteBoard() throws Exception {
        mockMvc.perform(delete("/board/{boardId}",36))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Delete Board Success"));
    }

    @Test
    void boardInquiry() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/board/inquiry/{page}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // 게시글이 없는 페이지 번호를 입력해도 테스트가 성공하는 문제 발견
    }

    @Test
    void boardTitleSearch() throws Exception {
        mockMvc.perform(get("/board/search")
                .param("title","3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void boardDetailInquiry() throws Exception {

        Board board = boardRepository.findByBoardId(36L).get();
        BoardDTO.boardDetailResDTO boardDetailResDTO = new BoardDTO.boardDetailResDTO(board);

        String requestBody = objectMapper.writeValueAsString(boardDetailResDTO);
        mockMvc.perform(get("/board/inquiry/detail/{boardId}",boardDetailResDTO.getBoardId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}