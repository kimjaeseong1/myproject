package com.example.withfuture.service;

import com.example.withfuture.dto.BoardDTO;
import com.example.withfuture.dto.Response;
import com.example.withfuture.entity.Board;
import com.example.withfuture.exception.board.BoardException;
import com.example.withfuture.exception.board.BoardErrorCode;
import com.example.withfuture.repository.BoardRepository;
import com.example.withfuture.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final HttpSession session;

    private final Response response;

    @Transactional
    public Board registerBoard(BoardDTO.BoardReqDTO boardReqDTO){
        try{
            if(session.getAttribute("id") == null){
                   throw new BoardException(BoardErrorCode.BOARD_ID_NULL);
            }else{
                Board board = Board.registerBoard(boardReqDTO);


            return boardRepository.save(board);
            }
        }catch(NullPointerException e){
            e.printStackTrace();
            throw new BoardException(BoardErrorCode.BOARD_REGISTER_FAILED);
        }
    }

    @Transactional
    public Board updateBoard( BoardDTO.updateBoardDTO updateBoardDTO){

        try{
                    Board board = boardRepository.findByBoardId(updateBoardDTO.getBoardId())
                            .orElseThrow(() -> new BoardException(BoardErrorCode.BOARD_UPDATE_FAILED));

                    board.updateBoard(updateBoardDTO.getTitle(), updateBoardDTO.getContent());

                    return board;
        }catch (BoardException e){
            e.printStackTrace();
            throw new BoardException(BoardErrorCode.BOARD_UPDATE_FAILED);
        }
    }

    @Transactional
    public Board deleteBoard(long boardId){
        try{

            Board board =boardRepository.findByBoardId(boardId).orElseThrow(() -> new BoardException(BoardErrorCode.BOARD_ID_NULL));

            boardRepository.deleteByBoardId(board.getBoardId()).get();
            return board;
        }catch(BoardException e){
            e.printStackTrace();
            throw new BoardException(BoardErrorCode.BOARD_DELETE_FAILED);
        }
    }

    public Page<Board> boardInquiry(Pageable pageable){
        try{

            //PageRequest pageRequest = PageRequest.of(page - 1, 100, Sort.by(Sort.Direction.ASC, "boardId"));

//            List<BoardDTO.boardListResDTO> boardList = boardRepository.findAllBy(pageRequest)
//                    .stream()
//                    .map(BoardDTO.boardListResDTO::new)
//                    .collect(Collectors.toList());

//            Slice<BoardDTO.boardListResDTO> boardList = boardRepository.findAllBy(pageRequest)
//                    .map(BoardDTO.boardListResDTO::new);


            return boardRepository.findAll(pageable);
        }catch(BoardException e){
            e.printStackTrace();
            throw new BoardException(BoardErrorCode.BOARD_LIST_FAILED);
        }
    }

    public Page<Board> boardTitleSearch(Pageable pageable, String title) {
        List<Board> titleList = boardRepository.findByTitleContaining(title);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Board> currentPageList;

        if (titleList.size() < startItem) {
            currentPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, titleList.size());
            currentPageList = titleList.subList(startItem, toIndex);
        }

        Page<Board> boardPage = new PageImpl<>(currentPageList, pageable, titleList.size());

        return boardPage;
    }
    public BoardDTO.boardDetailResDTO boardDetailInquiry(long boardId){
        try{
            Board board = boardRepository.findByBoardId(boardId).get();

            return new BoardDTO.boardDetailResDTO(board);
        }catch(BoardException e){
            e.printStackTrace();
            throw new BoardException(BoardErrorCode.BOARD_DETAIL_LIST_FAILED);
        }
    }
}
