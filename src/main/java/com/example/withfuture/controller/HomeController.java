package com.example.withfuture.controller;


import com.example.withfuture.dto.BoardDTO;
import com.example.withfuture.entity.Board;
import com.example.withfuture.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardController boardController;

    private final BoardService boardService;


    @GetMapping("/view/login")
    public String login(){
        return "userLogin";
    }

    @GetMapping("/view/board/write")
    public String boardRegisterView(){

        return "BoardWrite";
    }

    @GetMapping("/view/board/list")
    public String boardListView( Model model, @PageableDefault(sort = "boardId",direction = Sort.Direction.ASC) Pageable pageable){
        Page<BoardDTO.boardListResDTO> board = boardService.boardInquiry(pageable);
        model.addAttribute(board);
        return "boardList";
    }

    @GetMapping("/view/board/detail")
    public String boardDetail(Model model, @RequestParam long boardId){
        BoardDTO.boardDetailResDTO boardDetail = boardService.boardDetailInquiry(boardId);
        model.addAttribute("boardDetail", boardDetail);
        return "boardDetail";
    }

    @PostMapping("/view/board/delete/{boardId}")
    public String deleteBoard(@PathVariable long boardId) {
        // 게시글 삭제 로직을 호출하고, 성공하면 리스트 페이지로 리다이렉션
        boardService.deleteBoard(boardId);
        return "redirect:/view/board/list";
    }

    @GetMapping("/view/board/modify")
    public String boardModify( @RequestParam long boardId, Model model){

        model.addAttribute("board",boardController.boardDetailInquiry(boardId));
        return "boardModify";
    }

//    @PostMapping("/view/board/update/{boardId}")
//    public String boardUpdate( @PathVariable long boardId , BoardDTO.updateBoardDTO updateBoardDTO,Model model){
//      Board board =   boardService.updateBoard(boardId,updateBoardDTO);
//
//        return"redirect:/view/board/list";
//    }

}
