package com.example.demo.service.member;

import com.example.demo.dto.member.request.MemberCreateRequest;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void saveMember(MemberCreateRequest request) {
        memberRepository.save(new Member(request.getId(), request.getPassword(), request.getName(), request.getNickname(), request.getBirth(), request.getGender()));
    }
}
