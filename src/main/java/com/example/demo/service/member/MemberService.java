package com.example.demo.service.member;

import com.example.demo.dto.member.request.MemberDTO;
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
    public void saveMember(MemberDTO request) {
        // 이미 존재하는 아이디인지 확인
        if (memberRepository.existsById(request.getId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 존재하지 않는 아이디라면 회원가입 진행
        Member member = convertToEntity(request);
        memberRepository.save(member);
    }

    private Member convertToEntity(MemberDTO request) {
        return new Member(request.getId(), request.getPassword(), request.getName(),
                request.getNickname(), request.getBirth(), request.getGender());
    }
}