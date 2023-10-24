package com.example.withfuture.exception.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class MemberException extends RuntimeException{
    private ErrorCode errorCode;
    private String field;
    private String message;

    public MemberException(ErrorCode errorCode,String field){

        this.errorCode = errorCode;
        this.field = field;
    }
//    public MemberException(ErrorCode errorCode, String message) {
//        super(message);
//        this.errorCode = errorCode;
//    }

    public MemberException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public MemberException(ErrorCode errorCode,String field,String message){
        this.errorCode = errorCode;
        this.field = field;
        this.message = message;
    }
}
