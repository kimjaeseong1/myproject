package com.example.withfuture.dto;


import com.example.withfuture.entity.Board;
import com.example.withfuture.entity.Member;
import lombok.*;

import javax.validation.constraints.NotEmpty;

public class BoardDTO {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED )
    public static class BoardReqDTO{
        @NotEmpty(message = "{NotEmpty.message}")
        private String title;
        @NotEmpty(message="{NotEmpty.message}")
        private String content;

        @Builder
        public BoardReqDTO(String title, String content){
            this.title = title;
            this.content = content;
        }

        public Board toEntity(Member member){
            return Board.builder()
                    .member(member)
                    .title(title)
                    .content(content)
                    .build();
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
        private String memberId;

        public updateBoardDTO(String title,String content){
            this.title = title;
            this.content = content;
//            this.memberId = memberId;
        }

        public updateBoardDTO(String title,String content,String memberId){
            this.title = title;
            this.content = content;
            this.memberId = memberId;
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
        private String memberId;

        public boardDetailResDTO(Board board){
            this.boardId = board.getBoardId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.memberId = board.getMember().getMemberId();
        }
    }

}
