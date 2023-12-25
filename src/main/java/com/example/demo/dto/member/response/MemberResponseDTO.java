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
    private Long user_id;
    private String id;
    private String name;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private char gender;
    private int followers_cnt;
    private int following_cnt;

    public MemberResponseDTO(Member member) {
        this.user_id = member.getUserId();
        this.id = member.getId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.birth = member.getBirth();
        this.gender = member.getGender();
    }
    public MemberResponseDTO(Member member, int followers_cnt, int following_cnt) {
        this.user_id = member.getUserId();
        this.id = member.getId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.birth = member.getBirth();
        this.gender = member.getGender();
        this.followers_cnt = member.getFromUserList().size();
        this.following_cnt = member.getToUserList().size();
    }

    public MemberResponseDTO(MemberResponseDTO curUser, int followersCount, int followingCount) {
        this.user_id = curUser.getUser_id();
        this.id = curUser.getId();
        this.name = curUser.getName();
        this.nickname = curUser.getNickname();
        this.birth = curUser.getBirth();
        this.gender = curUser.getGender();
        this.followers_cnt = followersCount;
        this.following_cnt = followingCount;
    }
}