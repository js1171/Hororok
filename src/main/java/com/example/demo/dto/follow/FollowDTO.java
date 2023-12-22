package com.example.demo.dto.follow;

import com.example.demo.dto.member.request.MemberDTO;
import lombok.Getter;

@Getter
public class FollowDTO {
    private Long followId;
    private MemberDTO fromUser;
    private MemberDTO toUser;
}
