package com.example.demo.dto.member.response;

import com.example.demo.entity.Feed;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedResponseDTO {
    private Long feedId;
    private Long userId;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private int likesCount;
    private int commentCount;

    public FeedResponseDTO(Feed saveFeed) {
    }
    public FeedResponseDTO(Long feedId, Long userId, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.feedId = feedId;
        this.userId = userId;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public FeedResponseDTO(String contents) {
        this.contents = contents;
    }

    public FeedResponseDTO(Feed feed, int likeCount) {
        this.feedId = feed.getFeedId();
        this.userId = feed.getUserId();
        this.contents = feed.getContents();
        this.createdAt = feed.getCreatedAt();
        this.updatedAt = feed.getUpdatedAt();
        this.likesCount = feed.getLikesCnt().size();
    }

    public FeedResponseDTO(Feed feed, int likeCount, int commentCount) {
        this.feedId = feed.getFeedId();
        this.userId = feed.getUserId();
        this.contents = feed.getContents();
        this.createdAt = feed.getCreatedAt();
        this.updatedAt = feed.getUpdatedAt();
        this.likesCount = feed.getLikesCnt().size();
        this.commentCount = feed.getCommentList().size();
    }

    public int getLikesCount() {
        return likesCount;
    }

}
