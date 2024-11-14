package com.example.project.controller;

import com.example.project.dto.CommentDTO;
import com.example.project.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 저장
    @PostMapping("/comment/save")
    public ResponseEntity<?> save(@ModelAttribute CommentDTO commentDTO, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");

        commentDTO.setCommentWriter(loginId);

        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null) {
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/comment/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId, HttpSession session) {
        CommentDTO commentDTO = commentService.findById(commentId);
        String loginId = (String) session.getAttribute("loginId");

        // 댓글 작성자와 로그인 사용자 일치 여부 확인
        if (commentDTO != null && commentDTO.getCommentWriter().equals(loginId)) {
            commentService.delete(commentId);
            return ResponseEntity.ok().build(); // 삭제 성공 시 200 OK 반환
        } else if (commentDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글이 존재하지 않습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 삭제 권한이 없습니다.");
        }
    }
}

