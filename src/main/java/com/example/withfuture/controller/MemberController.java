package com.example.withfuture.controller;

import com.example.withfuture.dto.MemberDTO;
import com.example.withfuture.dto.Response;
import com.example.withfuture.service.MemberService;
import com.example.withfuture.service.MessageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final Response response;

    private final MessageService messageService;



    @ApiOperation(value = "회원가입 api",notes = "입력한 데이터 DB에 저장")
    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUpMember(@Valid @RequestBody MemberDTO.memberSignUpDTO memberSignUp){

        memberService.signMember(memberSignUp);
        String successMessage=messageService.getMessage("signup.successMessage");

         return ResponseEntity.ok(successMessage);
    }

    @ApiOperation(value = "로그인 api",notes = "로그인 하면서 session에 값 생성")
    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@Valid @RequestBody MemberDTO.loginMember loginMember){
         memberService.loginMember(loginMember);
        String successMessage = messageService.getMessage("login.successMessage");
        return response.success(successMessage);
    }


    @ApiOperation(value = "로그아웃 api",notes = "로그아웃 하면서 session의 값 삭제")
    @PostMapping("/logout")
    public ResponseEntity<?> logoutMember(){
         memberService.logoutMember();
        String successMessage = messageService.getMessage("logout.successMessage");
        return response.success(successMessage);
    }
}
