package com.example.withfuture.entity;


import com.example.withfuture.dto.MemberDTO;
import com.example.withfuture.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(name="member_role")
    private Role role = Role.ROLE_BRONZE;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList;



    public static Member registerMember(MemberDTO.memberSignUpDTO memberSignUpDTO){
        Member member = new Member();

        member.memberId = memberSignUpDTO.getMemberId();
        member.member_password = memberSignUpDTO.getPassword();
        member.memberName = memberSignUpDTO.getMemberName();
        member.role = (memberSignUpDTO.getRole() != null) ? memberSignUpDTO.getRole() : Role.ROLE_BRONZE;

        return member;
    }
}





