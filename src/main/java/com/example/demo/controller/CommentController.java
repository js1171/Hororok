package com.example.demo.controller;

import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    private final CommentService commentService;

    @Autowired
    private HttpSession httpSession;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

//    @PostMapping("/feeds/{feedId}/comments")
//    public ResponseEntity<String> createComment() {
//
//    }

}
