package com.example.demo.controller;

import com.example.demo.dto.member.request.CommentDTO;
import com.example.demo.dto.member.response.CommentResponseDTO;
import com.example.demo.dto.member.response.MemberResponseDTO;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    private final CommentService commentService;

    @Autowired
    private HttpSession httpSession;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/feeds/{feedId}/comments")
    public ResponseEntity<String> createComment(@PathVariable("feedId") Long feedId, HttpSession httpSession, @RequestBody CommentDTO dto) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if(userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다." );
        }
        commentService.createComment(dto, feedId, userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/feeds/{feedId}/comments")
    public ResponseEntity<Map<String, List<CommentResponseDTO>>> getComments(@PathVariable("feedId") Long feedId) {
        List<CommentResponseDTO> list = commentService.getComments(feedId);
        Map<String, List<CommentResponseDTO>> map = new HashMap<>();
        map.put("comments", list);
        return ResponseEntity.ok(map);
    }

    @PatchMapping("/feeds/comments/{commentId}")
    public ResponseEntity<String> updateComments(@PathVariable("commentId") Long commentId, @RequestBody CommentDTO dto, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if(userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다." );
        }
        commentService.updateComment(commentId, userId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/feeds/comments/{commentId}")
    public ResponseEntity<String> deleteComments(@PathVariable("commentId") Long commentId, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if(userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다." );
        }
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
