package com.example.demo.service.member;

import com.example.demo.dto.member.request.MemberDTO;
import com.example.demo.dto.member.response.MemberResponse;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    private HttpSession httpSession;

    @Transactional
    public void saveMember(MemberDTO request) {
        // 이미 존재하는 아이디인지 확인
        if (memberRepository.existsById(request.getId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 존재하지 않는 아이디라면 회원가입 진행
        Member member = convertToEntity(request);
        memberRepository.save(member);
    }

    public void loginMember(Member member) {
        httpSession.setAttribute("userId", member.getUserId());
    }

    public void logoutMember() {
        httpSession.removeAttribute("userId");
    }

    private Member convertToEntity(MemberDTO request) {
        return new Member(request.getId(), request.getPassword(), request.getName(),
                request.getNickname(), request.getBirth(), request.getGender());
    }

    @Transactional
    public List<MemberResponse> getMember(String id) {
        return memberRepository.findFirstById(id).stream().map(MemberResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public List<MemberResponse> getMembers() {
        return memberRepository.findAll().stream().map(MemberResponse::new).collect(Collectors.toList());
    }

}