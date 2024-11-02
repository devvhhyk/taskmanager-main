package com.example.project.controller;

import com.example.project.dto.TodoDTO;
import com.example.project.entity.MemberEntity;
import com.example.project.service.MemberService;
import com.example.project.service.TodoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final MemberService memberService;

    // 투두리스트 페이지
    @GetMapping("/todo")
    public String todoPage(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return "redirect:/member/login";
        }

        MemberEntity member = memberService.findByMemberId(loginId);
        List<TodoDTO> todoList = todoService.findAllByMember(member);
        model.addAttribute("todoList", todoList);
        return "todo";
    }

    // 투두리스트 저장
    @PostMapping("/todo/save")
    public String save(@ModelAttribute TodoDTO todoDTO, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");

        MemberEntity member = memberService.findByMemberId(loginId);
        todoService.save(todoDTO, member);
        return "todo";
    }

    // 투두리스트 목록
    @GetMapping("/todo/list")
    @ResponseBody
    public List<TodoDTO> list(HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");

        MemberEntity member = memberService.findByMemberId(loginId);
        return todoService.findAllByMember(member);
    }

    @PostMapping("/todo/update")
    public String update(@RequestBody TodoDTO todoDTO, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        MemberEntity memberEntity = memberService.findByMemberId(loginId);
        todoService.update(todoDTO, memberEntity);
        return "redirect:/todo";
    }

    // 투두리스트 완료
    @PostMapping("/todo/complete")
    @ResponseBody
    public ResponseEntity<?> complete(@RequestBody TodoDTO todoDTO) {
        todoService.complete(todoDTO);
        return ResponseEntity.ok().build();
    }

    // 투두리스트 삭제
    @PostMapping("/todo/delete")
    public String delete(@RequestBody TodoDTO todoDTO) {
        todoService.delete(todoDTO);
        return "todo";
    }
}