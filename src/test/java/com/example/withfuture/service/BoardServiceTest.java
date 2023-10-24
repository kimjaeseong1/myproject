package com.example.withfuture.service;

import com.example.withfuture.dto.BoardDTO;
import com.example.withfuture.entity.Board;
import com.example.withfuture.repository.BoardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    @WithMockUser
    @DisplayName("게시글 작성 성공 케이스")
    @Transactional
    @Test
    void registerBoard() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"memberId\":\"aaa@naver.com\", \"password\":\"aaaa1234!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login Success!!"));

        BoardDTO.BoardReqDTO boardDTO = BoardDTO.BoardReqDTO.builder()
                .title("refactoring")
                .content("ref APi 호출 테스트")
                .build();

        String url = "/board";

        String requestBody = objectMapper.writeValueAsString(boardDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .with(csrf())
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Board Register Success!!"));
    }


@WithMockUser
@DisplayName("게시글 수정 API 호출 테스트")
@Transactional
@Test
void updateBoardSuccess() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"memberId\":\"bbb@naver.com\", \"password\":\"bbbb1234!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login Success!!"));

    // 게시글 수정에 필요한 정보
    BoardDTO.updateBoardDTO updateBoardDTO = new BoardDTO.updateBoardDTO(6, "새로운 제목", "새로운 내용","bbb@naver.com");

    // 게시글 수정 API 호출
    mockMvc.perform(patch("/board/{boardId}", 6)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateBoardDTO))
                    .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Board Edit Success!!!"));
}



    @WithMockUser(username = "bbb@naver.com",authorities = {"ROLE_ADMIN"})
    @DisplayName("게시글 삭제 성공 케이스")
    @Transactional
    @Test
    void deleteBoard() throws Exception {
        mockMvc.perform(delete("/board/{boardId}",4)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Board Delete Success!!!"));
    }

    @DisplayName("게시글 조회 성공 케이스")
    @Test
    void boardInquiry() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/board/inquiry/{page}",0)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
        // 게시글이 없는 페이지 번호를 입력해도 테스트가 성공하는 문제 발견
    }


//    @DisplayName("게시글 검색 성공 케이스")
//    @Test
//    void boardTitleSearch() throws Exception {
//        mockMvc.perform(get("/board/search")
//                .param("title","a")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.content").isArray());
//    }

    @DisplayName("게시글 검색 API 호출 테스트")
    @Transactional
    @Test
    void searchBoardSuccess() throws Exception {

        String searchTitle = "a";

        // 게시글 검색 API 호출
        mockMvc.perform(get("/board/search")
                        .param("title", searchTitle)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content").isArray());
    }
    @DisplayName("게시글 상세 조회 성공 케이스")
    @Test
    void boardDetailInquiry() throws Exception {

        Board board = boardRepository.findByBoardId(5L).get();
        BoardDTO.boardDetailResDTO boardDetailResDTO = new BoardDTO.boardDetailResDTO(board);

        String requestBody = objectMapper.writeValueAsString(boardDetailResDTO);
        mockMvc.perform(get("/board/inquiry/detail/{boardId}",boardDetailResDTO.getBoardId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @WithMockUser
    @DisplayName("제목을 입력하지 않으면 게시글 작성 실패(영어)")
    @Transactional
    @Test
    void registerBoardLessTitle() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"memberId\":\"aaa@naver.com\", \"password\":\"aaaa1234!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login Success!!"));


        BoardDTO.BoardReqDTO boardDTO = BoardDTO.BoardReqDTO.builder()
                .content("ref APi 호출 테스트")
                .build();

        String url = "/board";

        String requestBody = objectMapper.writeValueAsString(boardDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .with(csrf())
                        .contentType("application/json")
                        .content(requestBody)
                        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ValidationError"))
                .andExpect(jsonPath("$.errors[0].message").value("This field must be entered."));
    }


    @DisplayName("세션이 null 일 때 실패(영어)")
    @Transactional
    @Test
    void registerBoardLessSession() throws Exception {

        BoardDTO.BoardReqDTO boardDTO = BoardDTO.BoardReqDTO.builder()
                .title("test")
                .content("ref APi 호출 테스트")
                .build();

        //Board board = Board.registerBoard(boardDTO);


        String url = "/board";

        String requestBody = objectMapper.writeValueAsString(boardDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isForbidden());
   }

    @WithMockUser
    @DisplayName("내용을 입력하지 않으면 게시글 작성 실패(영어)")
    @Transactional
    @Test
    void registerBoardLessTitle2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"memberId\":\"aaa@naver.com\", \"password\":\"aaaa1234!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login Success!!"));

        BoardDTO.BoardReqDTO boardDTO = BoardDTO.BoardReqDTO.builder()
                .title("게시글 제목 테스트")
                .build();

        String url = "/board";

        String requestBody = objectMapper.writeValueAsString(boardDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ValidationError"))
                .andExpect(jsonPath("$.errors[0].message").value("This field must be entered."));
    }

    @DisplayName("로그인 하지 않으면 게시글 작성 실패(영어)")
    @Transactional
    @Test
    void registerBoardNotUser() throws Exception {
        BoardDTO.BoardReqDTO boardDTO = BoardDTO.BoardReqDTO.builder()
                .title("resr")
                .content("fsefdf")
                .build();


        String url = "/board";

        String requestBody = objectMapper.writeValueAsString(boardDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @DisplayName("BoardId를 입력하지 않으면 udpate 실패(영어)")
    @Transactional
    @Test
    void updateBoardFail() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"memberId\":\"bbb@naver.com\", \"password\":\"bbbb1234!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login Success!!"));

        BoardDTO.updateBoardDTO updateBoard = new BoardDTO.updateBoardDTO("wer","wer");


        String requestBody = objectMapper.writeValueAsString(updateBoard);

        mockMvc.perform(patch("/board/{boardId}",5)
                        .with(csrf())
                        .contentType(MediaType. APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("BOARD_ID_NULL"))
                .andExpect(jsonPath("$.errors[0].message").value("boardId Null"));
    }

    @WithMockUser
    @DisplayName("로그인한 사용자가 게시글 수정 권한이 없을 때 udpate 실패(영어)")
    @Transactional
    @Test
    void updateBoardFail2() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"memberId\":\"aaa@naver.com\", \"password\":\"aaaa1234!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login Success!!"));

        BoardDTO.updateBoardDTO updateBoard = new BoardDTO.updateBoardDTO(6,"wer","wer","bbb@naver.com");


        String requestBody = objectMapper.writeValueAsString(updateBoard);

        mockMvc.perform(patch("/board/{boardId}",6)
                        .with(csrf())
                        .contentType(MediaType. APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BOARD_UPDATE_FAILED"))
                .andExpect(jsonPath("$.errors[0].message").value("You do not have permission to update."));

    }


    @WithMockUser(username = "ccc@naver.com",authorities = {"ROLE_ADMIN"})
    @DisplayName("boardId 없으면 삭제 실패(영어)")
    @Transactional
    @Test
    void deleteBoardFail() throws Exception {
        long nonExistentBoardId = 250; // 존재하지 않는 게시물 ID를 사용

        String url = "/board/" + nonExistentBoardId;

        mockMvc.perform(MockMvcRequestBuilders.delete(url)
                        .with(csrf()))
                .andExpect(status().isNotFound()) // 상태 코드 404 Not Found를 기대
                .andExpect(jsonPath("$.code").value("BOARD_ID_NULL"))
                .andExpect(jsonPath("$.errors[0].message").value("boardId Null"));
    }

    @DisplayName("boardId 없으면 게시글 상세조회 실패 (영어)")
    @Transactional
    @Test
    void detailBoard() throws  Exception{
        long BoardId = 38;
        String url = "/board/inquiry/detail/" + BoardId;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BOARD_DETAIL_LIST_FAILED"))
                .andExpect(jsonPath("$.errors[0].message").value("Board Detail List Failed"));
    }

    @DisplayName("검색어랑 게시글 제목이랑 맞지 않으면 실패 (영어)")
    @Transactional
    @Test
    void searchBoardFail() throws  Exception{
        String searchTitle = "asdfwer";

        mockMvc.perform(get("/board/search")
                        .param("title", searchTitle)
                        .contentType("application/json"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("BOARD_LIST_FAILED"))
                .andExpect(jsonPath("$.errors[0].message").value("This post does not exist."));


    }

}