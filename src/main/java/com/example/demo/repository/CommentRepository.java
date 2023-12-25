package com.example.demo.repository;

import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByFeedId(Long feedId);
    Optional<Comment> findByCommentId(Long commentId);
    Optional<Long> findUserIdByCommentId(Long commentId);

}
