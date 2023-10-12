package com.example.withfuture.exception.board;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardException extends RuntimeException {
    private BoardErrorCode boardErrorCode;
    private String message;
    public BoardException(BoardErrorCode boardErrorCode) {
        this.boardErrorCode = boardErrorCode;
    }

}
