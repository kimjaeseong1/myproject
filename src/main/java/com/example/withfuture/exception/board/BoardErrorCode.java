package com.example.withfuture.exception.board;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardErrorCode {

    BOARD_LIST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Requested Signup Failed"),
    BOARD_REGISTER_FAILED(HttpStatus.BAD_REQUEST,"BOARD REGISTER FAILED"),
    BOARD_UPDATE_FAILED(HttpStatus.BAD_REQUEST,"Requested UPDATE Failed"),
    BOARD_DELETE_FAILED(HttpStatus.BAD_REQUEST,"BOARD DELETE FAILED"),
    BOARD_ID_NULL(HttpStatus.NOT_FOUND,"BoardId Null"),
    BOARD_DETAIL_LIST_FAILED(HttpStatus.BAD_REQUEST,"Board Detail Failed");

    private final HttpStatus status;
    private final String message;
}
