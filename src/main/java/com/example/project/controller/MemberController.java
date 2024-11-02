package com.example.project.controller;

import com.example.project.dto.MemberDTO;
import com.example.project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입폼
    @GetMapping("/member/signup")
    public String saveForm() {
        return "signup";
    }

    @PostMapping("/member/signup")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "login";
    }

    // 로그인폼
    @GetMapping("/member/login")
    public String loginForm(HttpSession session) {
        if (session.getAttribute("loginId") != null) {
            return "redirect:/member/mypage";
        }
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            session.setAttribute("loginId", loginResult.getMemberId());
            return "redirect:/member/mypage";
        } else {
            model.addAttribute("error", "아이디나 비밀번호가 일치하지 않습니다.");
            return "login";
        }
    }

    // 로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/member/login";
    }

    // 마이페이지
    @GetMapping("/member/mypage")
    public String mypage(HttpSession session, Model model) {
        model.addAttribute("loginId", session.getAttribute("loginId"));
        return "mypage";
    }
}