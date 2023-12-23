package com.example.demo.dto.member.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDTO {
    private Long comment_id;
    private Long feed_id;
    private Long user_id;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated_at;

    private MemberResponseDTO user;

    public CommentResponseDTO(Long commentId, Long feedId, Long userId, String contents, LocalDateTime createdAt, LocalDateTime updatedAt, MemberResponseDTO user) {
        this.comment_id = commentId;
        this.feed_id = feedId;
        this.user_id = userId;
        this.contents = contents;
        this.created_at = createdAt;
        this.updated_at = updatedAt;
        this.user = user;
    }
}
