package com.example.demo.entity;

import com.example.demo.dto.member.request.FeedRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "feed")
@NoArgsConstructor
public class Feed {
    @Id
    @Column(name = "feed_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column(name = "contents")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "feed", fetch=FetchType.LAZY)
    private List<FeedLike> likesCnt = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public Feed(FeedRequestDTO feedRequestDTO, Member member) {
        this.contents = feedRequestDTO.getContents();
        this.member = member;
        this.createdAt = LocalDateTime.now();
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}