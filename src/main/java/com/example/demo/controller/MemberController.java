package com.example.demo.controller;

import com.example.demo.dto.member.request.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {

    @Autowired
    MemberRepository memberRepository;
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @ResponseBody
    @PostMapping("/register")
    public void saveMember(@RequestBody MemberDTO request) {
        memberService.saveMember(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody Map<String, String> params) {
        Member member = memberRepository.findByIdAndPassword(params.get("id"), params.get("password"));

        if (member != null) {
            System.out.println("당신이 찾고자 하는 멤버의 고유 번호는 " + member.getUserId());
            return ResponseEntity.ok(member.getUserId());
        }
        return ResponseEntity.ok(0L);

    }

}