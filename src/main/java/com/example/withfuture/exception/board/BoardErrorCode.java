package com.example.withfuture.exception.board;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardErrorCode {

    BOARD_LIST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "BOARD_LIST_ERROR"),
    BOARD_REGISTER_FAILED(HttpStatus.BAD_REQUEST,"BOARD_REGISTER_ERROR"),
    BOARD_UPDATE_FAILED(HttpStatus.BAD_REQUEST,"BOARD_UPDATE_ERROR"),
    BOARD_DELETE_FAILED(HttpStatus.BAD_REQUEST,"BOARD_DELETE_ERROR"),
    BOARD_ID_NULL(HttpStatus.NOT_FOUND,"BOARD_ID_NULL_ERROR"),
    BOARD_DETAIL_LIST_FAILED(HttpStatus.BAD_REQUEST,"BOARD_DETAIL_LIST_ERROR");

    private final HttpStatus status;
    private final String message;
}
