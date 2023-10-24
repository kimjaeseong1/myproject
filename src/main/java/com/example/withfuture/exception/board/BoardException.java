package com.example.withfuture.exception.board;


import com.example.withfuture.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class BoardException extends RuntimeException {
    private BoardErrorCode boardErrorCode;
    private String message;
    public BoardException(BoardErrorCode boardErrorCode) {
        this.boardErrorCode = boardErrorCode;
        //this.message = boardErrorCode.getMessage();
    }

    public BoardException(BoardErrorCode boardErrorCode,String message){
        this.boardErrorCode = boardErrorCode;
        this.message = message;
    }

}
