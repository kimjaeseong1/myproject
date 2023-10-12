package com.example.withfuture.dto;

import com.example.withfuture.entity.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class MemberDTO {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class memberSignUpDTO extends  MemberDTO{
        @NotEmpty(message = "{NotEmpty.message}")
        @Email
        private String memberId;

        @Length(max = 20, message = "{memberName.Length.message}")
        private String memberName;

//      @NotEmpty(message = "{password.NotEmpty.message}")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "{password.Pattern.message}")
        private String password;

        @Builder
        public memberSignUpDTO(String memberId, String password, String memberName) {
            this.memberId = memberId;
            this.password = password;
            this.memberName = memberName;
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class loginMember{

        @NotEmpty(message = "{NotEmpty.message}")
        @Email()
        private String memberId;

        @NotEmpty(message = "{password.NotEmpty.message}")
        private String password;

        @Builder
        public loginMember(String memberId, String password){
            this.memberId = memberId;
            this.password = password;

        }

    }

}
