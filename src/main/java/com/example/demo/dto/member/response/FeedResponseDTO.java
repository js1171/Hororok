package com.example.demo.dto.member.response;

import com.example.demo.entity.Feed;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedResponseDTO {
    private Long feedId;
    private Long userId;
    private String contents;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private int likesCount;
    private int commentCount;

    public FeedResponseDTO(Feed saveFeed) {
    }

    public FeedResponseDTO(Feed feed, int likesCount, int commentCount) {
        this.feedId = feed.getFeedId();
        this.userId = feed.getUserId();
        this.contents = feed.getContents();
        this.createdAt = feed.getCreatedAt();
        this.updatedAt = feed.getUpdatedAt();
        this.likesCount = feed.getLikesCnt().size();
        this.commentCount = feed.getCommentList().size();
    }

    public Long getFeedId() {
        return feedId;
    }

    public Long getUserId() {
        return userId;
    }

}
