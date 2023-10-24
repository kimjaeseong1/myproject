package com.example.withfuture.controller;

import com.example.withfuture.dto.BoardDTO;
import com.example.withfuture.dto.Response;
import com.example.withfuture.entity.Board;
import com.example.withfuture.service.BoardService;
import com.example.withfuture.service.MessageService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final Response response;
    private final MessageService messageService;

    @ApiOperation(value = "게시글 작성 api",notes = "게시글을 등록한다..")
    @ResponseBody
    @PostMapping("/board")
    public ResponseEntity<?> registerBoard(@Valid @RequestBody BoardDTO.BoardReqDTO boardReqDTO){
        boardService.registerBoard(boardReqDTO);
        String successMessage  = messageService.getMessage("board.successRegister");
        return response.success(successMessage);
    }

    @ResponseBody
    @ApiOperation(value = "게시글 수정 api",notes = "게시글을 수정한다.")
    @PatchMapping ("/board/{boardId}")
    public ResponseEntity updateBoard( @RequestBody BoardDTO.updateBoardDTO updateBoardDTO){

        log.info("Received updateBoardDTO: {}", updateBoardDTO);
        String successMessage = messageService.getMessage("board.successEdit");
        boardService.updateBoard(updateBoardDTO);
        return response.success(successMessage);

    }

    @ApiOperation(value = "게시글 삭제 api", notes = "게시글을 삭제한다.")
    @ResponseBody
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable long boardId){

        boardService.deleteBoard(boardId);
        String successMessage = messageService.getMessage("board.successDelete");
        return response.success(successMessage);
    }

    @ApiOperation(value = "게시글 전체 조회 API", notes = "게시글을 전체 조회한다.")
    @ResponseBody
    @GetMapping("/board/inquiry/{page}")
    public Page<BoardDTO.boardListResDTO> boardInquiry(@PageableDefault(sort = "boardId",direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Integer page){
        Pageable modifiedPageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());

        Page<BoardDTO.boardListResDTO> board = boardService.boardInquiry(modifiedPageable);
        PageImpl<BoardDTO.boardListResDTO> pagedBoard = new PageImpl<>(board.getContent(), pageable, board.getTotalElements());

        return pagedBoard;
    }

    @ApiOperation(value=" 상세 게시글 조회 api", notes = "게시글 상제 조회한다.")
    @ResponseBody
    @GetMapping("/board/inquiry/detail/{boardId}")
    public BoardDTO.boardDetailResDTO boardDetailInquiry(@PathVariable long boardId){
        return boardService.boardDetailInquiry(boardId);
    }

    @ApiOperation(value = "게시글 제목 검색 API", notes = "게시글 제목을 검색한다.")
    @ResponseBody
    @GetMapping("/board/search")
    public Page<BoardDTO.boardListResDTO> boardSearch(@PageableDefault(sort = "boardId", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(name = "title") String title) {
        Page<BoardDTO.boardListResDTO> board = boardService.boardTitleSearch(pageable, title);

        return board;
    }
}
