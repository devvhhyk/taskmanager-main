package com.example.project.controller;

import com.example.project.dto.BoardDTO;
import com.example.project.dto.CommentDTO;
import com.example.project.service.BoardService;
import com.example.project.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    // 게시글 목록
    @GetMapping("/board")
    public String findAll(@PageableDefault(page = 1) Pageable pageable, HttpSession session, Model model) {
        if (session.getAttribute("loginId") == null) {
            return "redirect:/member/login";
        }

        // 게시글 목록 페이징 처리
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("loginId", session.getAttribute("loginId"));
        return "board";
    }

    // 게시글 저장
    @GetMapping("/board/save")
    public String saveForm(HttpSession session, Model model) {
        model.addAttribute("loginId", session.getAttribute("loginId"));
        return "boardSave";
    }

    @PostMapping("/board/save")
    public String save(@ModelAttribute BoardDTO boardDTO, HttpSession session) throws IOException {
        String memberId = (String) session.getAttribute("loginId");
        boardService.save(boardDTO, memberId);
        return "redirect:/board";
    }

    // 게시글 상세
    @GetMapping("/board/{id}")
    public String findById(@PathVariable("id") Long id, Model model, HttpSession session,
                           @PageableDefault(page = 1) Pageable pageable) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findAll(id);

        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("loginId", session.getAttribute("loginId"));
        return "boardDetail";
    }

    // 게시글 수정
    @GetMapping("/board/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        BoardDTO boardDTO = boardService.findById(id);
        String loginId = (String) session.getAttribute("loginId");
        if (!boardDTO.getMemberId().equals(loginId)) {
            return "redirect:/board";
        }
        model.addAttribute("boardUpdate", boardDTO);
        return "boardUpdate";
    }

    @PostMapping("board/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model, HttpSession session) {
        BoardDTO board = boardService.update(boardDTO);
        List<CommentDTO> commentDTOList = commentService.findAll(boardDTO.getId());

        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", board);
        model.addAttribute("loginId", session.getAttribute("loginId"));
        return "boardDetail";
    }

    // 게시글 삭제
    @GetMapping("board/delete/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        BoardDTO boardDTO = boardService.findById(id);
        String loginId = (String) session.getAttribute("loginId");
        if (boardDTO.getMemberId().equals(loginId)) {
            boardService.delete(id);
        }
        return "redirect:/board";
    }
}