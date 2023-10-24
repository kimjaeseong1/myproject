package com.example.withfuture.entity;


import com.example.withfuture.dto.BoardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private long boardId;

    @Column(name= "board_title")
    private String title;

    @Column(name = "board_content")
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


    public static Board registerBoard(BoardDTO.BoardReqDTO boardReqDTO, Member member){
       Board board  = new Board();
       board.title = boardReqDTO.getTitle();
       board.content = boardReqDTO.getContent();
       board.member = member;
       return board;
    }

    public void updateBoard(String title, String content){
        this.title = title;
        this.content = content;
    }
}
