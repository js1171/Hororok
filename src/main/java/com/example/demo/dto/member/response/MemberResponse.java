package com.example.demo.dto.member.response;

import com.example.demo.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class MemberResponse {
    private long userId;
    private String id;

    public MemberResponse(Member member) {
        this.userId = member.getUserId();
        this.id = member.getId();
    }

    public MemberResponse(long userId, String id) {
        this.userId = userId;
        this.id = id;
    }

}
