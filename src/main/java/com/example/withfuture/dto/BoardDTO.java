package com.example.withfuture.dto;


import com.example.withfuture.entity.Board;
import lombok.*;

import javax.validation.constraints.NotEmpty;

public class BoardDTO {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED )
    public static class BoardReqDTO{
        @NotEmpty(message = "{NotEmpty.message=This field must be entered.}")
        private String title;
        @NotEmpty(message=" {NotEmpty.message=This field must be entered.} ")
        private String content;

        @Builder
        public BoardReqDTO(String title, String content){
            this.title = title;
            this.content = content;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    @Setter
    public static class updateBoardDTO{
        private long boardId;
        private String title;
        private String content;

        public updateBoardDTO(String title,String content){
            this.title = title;
            this.content = content;
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class boardListResDTO{
        private long boardId;
        private String title;

        @Builder
        public boardListResDTO(Board board){
            this.boardId = board.getBoardId();
            this.title = board.getTitle();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class boardDetailResDTO{
        private long boardId;
        private String title;
        private String content;

        public boardDetailResDTO(Board board){
            this.boardId = board.getBoardId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

}
