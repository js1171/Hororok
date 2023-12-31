package com.example.demo.entity;

import com.example.demo.dto.member.request.CommentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private Member member;

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;


    @Column(name = "feed_id")
    private Long feedId;

    @Column(name = "user_id")
    private Long userId;

    private String contents;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", insertable=false, updatable=false)
    private Feed feed;

    public Comment (CommentDTO dto, Long feedId, Long userId) {
        this.feedId = feedId;
        this.userId = userId;
        this.contents = dto.getContents();
    }

    public void updateComment(String contents) {
        this.contents = contents;
    }

    public Member getUser() {
        return member;
    }
}
