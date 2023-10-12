package com.example.withfuture.repository;

import com.example.withfuture.dto.BoardDTO;
import com.example.withfuture.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {


    Optional<Board> findByBoardId(Long boardId);

    Optional<Board> deleteByBoardId(long boardId);



    List<Board> findByTitleContaining(String title);
    Slice<Board> findAllBy(PageRequest pageRequest);

    Page<Board>findAllBy(Pageable pageable);
}
