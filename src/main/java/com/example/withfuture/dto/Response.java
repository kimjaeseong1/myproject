package com.example.withfuture.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Response<T> {
 /*===========================
       Response 구성
    ===========================*/
    @Getter
    @Builder
    private static class Body {
     private int state;
     private String result;
     private String message;
     private Object data;
    }
    public ResponseEntity<?> success(Object data, String msg, HttpStatus status){
        Body body = Body.builder().state(status.value())
                .result("SUCCESS")
                .message(msg)
                .data(data)
                .build();
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<?> success(Object data, String msg) {
        return success(data, msg, HttpStatus.OK);
    }

    public ResponseEntity<?> success(String msg){
        return success(null, msg, HttpStatus.OK);
    }

    public ResponseEntity<?> success(Object data){return success(data,null,null);}

    //failed
    public ResponseEntity<?> fail(Object data, String msg, HttpStatus status) {
        Body body = Body.builder()
                .state(status.value())
                .result("FAIL")
                .message(msg)
                .data(data)
                .build();
        return ResponseEntity.internalServerError().body(body);
    }


    public ResponseEntity<?> failed(String msg){return fail(null, msg, HttpStatus.BAD_REQUEST);}

}
