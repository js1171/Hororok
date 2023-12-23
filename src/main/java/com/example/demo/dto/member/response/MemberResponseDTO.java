package com.example.demo.dto.member.response;

import com.example.demo.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class MemberResponseDTO {
    private Long userId;
    private String id;
    private String name;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private char gender;

    public MemberResponseDTO(Member member) {
        this.userId = member.getUserId();
        this.id = member.getId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.birth = member.getBirth();
        this.gender = member.getGender();
    }
}