package com.example.demo.dto.member.response;

import com.example.demo.entity.Feed;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedResponseDTO {
    private Long feed_id;
    private Long user_id;
    private String contents;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated_at;

    private int likes_cnt;
    private int comment_cnt;
    private MemberResponseDTO user;

    public FeedResponseDTO(Feed saveFeed) {
    }

    public FeedResponseDTO(Feed feed, int likesCount, int commentCount) {
        this.feed_id = feed.getFeedId();
        this.user_id = feed.getUserId();
        this.contents = feed.getContents();
        this.created_at = feed.getCreatedAt();
        this.updated_at = feed.getUpdatedAt();
        this.likes_cnt = feed.getLikesCnt().size();
        this.comment_cnt = feed.getCommentList().size();
        this.user = new MemberResponseDTO(feed.getMember());
    }



}
