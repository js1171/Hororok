package com.example.demo.service;

import com.example.demo.dto.member.request.MemberRequestDTO;
import com.example.demo.dto.member.request.MemberUpdateDTO;
import com.example.demo.dto.member.response.MemberResponseDTO;
import com.example.demo.entity.FeedLike;
import com.example.demo.entity.Member;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    private final LikeRepository likeRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository, LikeRepository likeRepository) {
        this.memberRepository = memberRepository;
        this.likeRepository = likeRepository;
    }

    @Autowired
    private HttpSession httpSession;

    @Transactional
    public void saveMember(MemberRequestDTO request) {
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

    private Member convertToEntity(MemberRequestDTO request) {
        return new Member(request.getId(), request.getPw(), request.getName(),
                request.getNickname(), request.getBirth(), request.getGender());
    }

    @Transactional
    public List<MemberResponseDTO> getMember(Long userId) {
        return memberRepository.findFirstByUserId(userId).stream().map(MemberResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public List<MemberResponseDTO> getMembers() {
        return memberRepository.findAll().stream().map(MemberResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Long> getLikedFeeds(Long userId) {
        List<FeedLike> likedFeeds = likeRepository.findByMemberUserId(userId);
        return likedFeeds.stream().map(feedLike -> feedLike.getFeed().getFeedId()).collect(Collectors.toList());
    }

    @Transactional
    public void updateMember(Long userId, MemberUpdateDTO dto) {
        Member member = memberRepository.findFirstByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);

        member.updateMember(dto.getPassword(), dto.getName(), dto.getNickname(), dto.getBirth(), dto.getGender());
        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Long userId) {
        Member member = memberRepository.findFirstByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public Member findMemberById(Long userId) {
        return memberRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    @Transactional(readOnly = true)
    public MemberResponseDTO findMemberByUserId(Long userId) {
        Member member = memberRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return new MemberResponseDTO(member);
    }
}