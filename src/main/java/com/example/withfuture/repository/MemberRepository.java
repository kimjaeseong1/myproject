package com.example.withfuture.repository;

import com.example.withfuture.dto.MemberDTO;
import com.example.withfuture.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member,String> {

    Optional<Member> findByMemberId(String memberId);
}
