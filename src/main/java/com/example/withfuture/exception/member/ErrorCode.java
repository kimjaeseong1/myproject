package com.example.withfuture.exception.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "Requested Signup Failed"),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED,"Requested login Failed"),
    SESSION_ID_NULL(HttpStatus.NOT_FOUND,"Session Id Null"),
    MEMBERID_NULL(HttpStatus.BAD_REQUEST,"해당 아이디가 없습니다."),
    LOGIN_PWD_FAILED(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다."),
    LOGOUT_FAILED(HttpStatus.NOT_FOUND,"LogOut Failed");

    private final HttpStatus status;
    private final String message;
}
