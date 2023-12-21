package com.example.demo.service;

import com.example.demo.dto.member.request.CommentDTO;
import com.example.demo.dto.member.request.MemberDTO;
import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    private HttpSession httpSession;

    public void createComment(CommentDTO dto, Long feedId, Long userId) {
        Comment comment = new Comment(dto, feedId, userId);
        commentRepository.save(comment);
    }


}
