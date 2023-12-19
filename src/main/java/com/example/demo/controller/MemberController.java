package com.example.demo.controller;

import com.example.demo.dto.member.request.MemberCreateRequest;
import com.example.demo.service.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MemberController {

    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService=memberService;
    }

    @PostMapping("/register")
    public void saveMember(@RequestBody MemberCreateRequest request) {
        memberService.saveMember(request);
    }
}
