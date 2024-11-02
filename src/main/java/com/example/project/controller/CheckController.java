package com.example.project.controller;

import com.example.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckController {

    private final MemberService memberService;

    // 아이디 중복체크
    @PostMapping("/user/checkId")
    public ResponseEntity<Boolean> idCheck(@RequestParam("memberId") String memberId){
        boolean result = memberService.isMemberIdAvailable(memberId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 이메일 중복체크
    @PostMapping("/user/checkEmail")
    public ResponseEntity<Boolean> emailCheck(@RequestParam("memberEmail") String memberEmail){
        boolean result = memberService.isMemberEmailAvailable(memberEmail);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
