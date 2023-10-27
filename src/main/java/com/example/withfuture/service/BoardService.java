package com.example.withfuture.service;

import com.example.withfuture.dto.BoardDTO;
import com.example.withfuture.dto.Response;
import com.example.withfuture.entity.Board;
import com.example.withfuture.entity.Member;
import com.example.withfuture.exception.board.BoardException;
import com.example.withfuture.exception.board.BoardErrorCode;
import com.example.withfuture.exception.member.ErrorCode;
import com.example.withfuture.exception.member.MemberException;
import com.example.withfuture.repository.BoardRepository;
import com.example.withfuture.repository.MemberRepository;
import com.example.withfuture.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final MessageService messageService;


    @Transactional
    public Board registerBoard(BoardDTO.BoardReqDTO boardReqDTO){

        String currentUsername = SecurityUtils.getCurrentUsername();
        System.out.println("currentUsername= " +currentUsername);
        if(currentUsername == null){
                throw new MemberException(ErrorCode.LOGIN_USER_NULL);
            }else {
            Member member = memberRepository.findByMemberId(currentUsername)
                    .orElseThrow(() -> new MemberException(ErrorCode.MEMBERID_NULL,"memberId"));
                return boardRepository.save(boardReqDTO.toEntity(member));
            }
    }

    @Transactional
    public Board updateBoard(BoardDTO.updateBoardDTO updateBoardDTO) {
        Board board = boardRepository.findByBoardId(updateBoardDTO.getBoardId())
                .orElseThrow(() -> new BoardException(BoardErrorCode.BOARD_ID_NULL));

        String currentUsername = SecurityUtils.getCurrentUsername();

        if(currentUsername != null &&board.getMember().getMemberId().equals(currentUsername)){
            board.updateBoard(updateBoardDTO.getTitle(), updateBoardDTO.getContent());
            return board;
        }else{
            throw new BoardException(BoardErrorCode.BOARD_UPDATE_FAILED, messageService.getMessage("CUSTOM_UPDATE_ERROR"));
        }
    }

    @Transactional
    public Board deleteBoard(long boardId){
        Board board = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new BoardException(BoardErrorCode.BOARD_ID_NULL));

        Set<String> userRole = SecurityUtils.getCurrentUserRoles();

        if(userRole.contains("ROLE_ADMIN")){

            boardRepository.deleteByBoardId(board.getBoardId()).get();
            return board;
        }else{
            throw new BoardException(BoardErrorCode.BOARD_DELETE_FAILED, messageService.getMessage("CUSTOM_DELETE_ERROR"));
        }
    }

//    public Page<Board> boardInquiry(Pageable pageable){
//        return boardRepository.findAll(pageable);
//    }
    public Page<BoardDTO.boardListResDTO> boardInquiry(Pageable pageable){
        Page<Board> boards =  boardRepository.findAll(pageable);
        List<BoardDTO.boardListResDTO> boardDTO = new ArrayList<>();

        for(Board board : boards){
            BoardDTO.boardListResDTO result = BoardDTO.boardListResDTO.builder()
                    .board(board)
                    .build();
            boardDTO.add(result);
        }
        return new PageImpl<>(boardDTO,pageable,boards.getTotalElements());
}


    public Page<BoardDTO.boardListResDTO> boardTitleSearch(Pageable pageable, String title) {
        // 게시글을 검색합니다.
        Page<Board> boards = boardRepository.findByTitleContaining(title, pageable);

        // 검색 결과가 없으면 예외를 던집니다.
        if (boards.isEmpty()) {
            throw new BoardException(BoardErrorCode.BOARD_LIST_FAILED, messageService.getMessage("CUSTOM_SEARCH_ERROR"));
        }

        // 검색 결과를 DTO로 매핑합니다.
        List<BoardDTO.boardListResDTO> boardDTO = boards.getContent().stream()
                .map(board -> BoardDTO.boardListResDTO.builder().board(board).build())
                .collect(Collectors.toList());

        return new PageImpl<>(boardDTO, pageable, boards.getTotalElements());
    }

    public BoardDTO.boardDetailResDTO boardDetailInquiry(long boardId){
        Board board = boardRepository.findByBoardId(boardId).orElseThrow(
                () -> new BoardException(BoardErrorCode.BOARD_DETAIL_LIST_FAILED));

        return new BoardDTO.boardDetailResDTO(board);
    }


    public boolean isAuthorOfBoard(Long boardId, String username){
        Optional<Board> boardOptional = boardRepository.findByBoardId(boardId);
        if(boardOptional.isPresent()){
            Board board = boardOptional.get();
            return board.getMember().getMemberId().equals(username);
        }
        return false;
    }


}
