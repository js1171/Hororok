package com.example.demo.dto.member.response;

import com.example.demo.entity.Feed;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedResponseDTO {
    private Long feedId;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public FeedResponseDTO(Feed saveFeed) {
    }
}
