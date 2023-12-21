package com.example.demo.service;

import com.example.demo.dto.member.request.CommentDTO;
import com.example.demo.dto.member.request.MemberDTO;
import com.example.demo.dto.member.response.CommentResponseDTO;
import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CommentResponseDTO> getComments(Long feedId) {
        List<Comment> comments = commentRepository.findByFeedId(feedId);
        return comments.stream().map(comment ->
                new CommentResponseDTO(comment.getCommentId(), comment.getFeedId(), comment.getUserId(), comment.getContents(), comment.getCreatedAt(), comment.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    public void updateComment(Long commentId, Long userId, CommentDTO dto) {
        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(IllegalArgumentException::new);
        comment.updateComment(dto.getContents());
        commentRepository.save(comment);


    }


}
