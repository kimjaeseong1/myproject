package com.example.withfuture.exception.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException{
    private ErrorCode errorCode;
    public MemberException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
