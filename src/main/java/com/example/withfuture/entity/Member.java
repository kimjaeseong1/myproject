package com.example.withfuture.entity;


import com.example.withfuture.dto.MemberDTO;
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
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "memberId")
    private String memberId;

    @Column(name = "memberName")
    private String memberName;

    @Column(name="member_password")
    private String member_password;


    public static Member registerMember(MemberDTO.memberSignUpDTO memberSignUpDTO){
        Member member = new Member();

        member.memberId = memberSignUpDTO.getMemberId();
        member.member_password = memberSignUpDTO.getPassword();
        member.memberName = memberSignUpDTO.getMemberName();

        return member;
    }
}





