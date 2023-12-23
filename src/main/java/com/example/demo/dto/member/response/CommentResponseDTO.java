package com.example.demo.dto.member.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDTO {
    private Long commentId;
    private Long feedId;
    private Long userId;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    public CommentResponseDTO(Long commentId, Long feedId, Long userId, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.feedId = feedId;
        this.userId = userId;
        this.contents=contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
