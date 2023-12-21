package com.example.demo.dto.member.response;

import com.example.demo.entity.Member;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class MemberResponse {
    private Long userId;
    private String id;
    private String password;
    private String name;
    private String nickname;
    private LocalDate birth;
    private char gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MemberResponse(Member member) {
        this.userId = member.getUserId();
        this.id = member.getId();
        this.password = member.getPassword();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.birth = member.getBirth();
        this.gender = member.getGender();
        this.createdAt = member.getCreatedAt();
        this.updatedAt = member.getUpdatedAt();
    }

    public MemberResponse(long userId, String id) {
        this.userId = userId;
        this.id = id;
    }

}
