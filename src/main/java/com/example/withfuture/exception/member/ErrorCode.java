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
    SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "SIGNUP_ERROR"),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST,"LOGIN_FAILED_ERROR"),
    LOGIN_USER_NULL(HttpStatus.UNAUTHORIZED,"LOGIN_USER_ERROR"),
    MEMBERID_NULL(HttpStatus.BAD_REQUEST,"MEMBERID_NULL_ERROR"),
    LOGIN_PWD_FAILED(HttpStatus.UNAUTHORIZED,"LOGIN_PWD_ERROR"),
    LOGOUT_FAILED(HttpStatus.NOT_FOUND,"LOGOUT_FAILED_ERROR");

    private final HttpStatus status;
    private final String message;
}
