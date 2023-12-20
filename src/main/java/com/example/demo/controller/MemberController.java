package com.example.demo.controller;

import com.example.demo.dto.member.request.MemberDTO;
import com.example.demo.service.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService=memberService;
    }

    @ResponseBody
    @PostMapping("/register")
    public void saveMember(@RequestBody MemberDTO request) {
        memberService.saveMember(request);
    }
}
