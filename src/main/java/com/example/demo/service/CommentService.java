package com.example.demo.service;

import com.example.demo.dto.member.request.CommentDTO;
import com.example.demo.dto.member.response.CommentResponseDTO;
import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    public void createComment(CommentDTO dto, Long feedId, Long userId) {
        Comment comment = new Comment(dto, feedId, userId);
        commentRepository.save(comment);
    }

    @Transactional
    public List<CommentResponseDTO> getComments(Long feedId) {
        List<Comment> comments = commentRepository.findByFeedId(feedId);
        return comments.stream().map(comment ->
                new CommentResponseDTO(comment.getCommentId(), comment.getFeedId(), comment.getUserId(), comment.getContents(), comment.getCreatedAt(), comment.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long commentId, Long userId, CommentDTO dto) {
        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

         // 로그인 유저와 댓글 작성자가 일치한지 확인
        if(!userId.equals(comment.getMember().getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 수정 권한이 없습니다.");
        }

        if (dto.getContents() != null) {
            comment.updateComment(dto.getContents());
            commentRepository.save(comment);
        }
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        // 로그인 유저와 댓글 삭제 요청자가 일치한지 확인
        if(!userId.equals(comment.getMember().getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);

    }


}
