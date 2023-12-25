package com.example.demo.controller;

import com.example.demo.dto.member.request.MemberRequestDTO;
import com.example.demo.dto.member.request.MemberUpdateDTO;
import com.example.demo.dto.member.response.MemberResponseDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MemberController {
    @Autowired
    MemberRepository memberRepository;
    private final MemberService memberService;

    @Autowired
    private HttpSession httpSession;
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public void saveMember(@RequestBody MemberRequestDTO request) {
        if(request.getId().isEmpty()|| request.getPw().isEmpty() || request.getName().isEmpty()||request.getNickname().isEmpty() || request.getGender() == '\u0000'){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        memberService.saveMember(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody Map<String, String> params) {
        Member member = memberRepository.findByIdAndPw(params.get("id"), params.get("pw"));
        if (member != null) {
            memberService.loginMember(member); // 세션에 사용자 정보 저장
            httpSession.setMaxInactiveInterval(30 * 60); // 세션의 최대 유지 시간을 5분으로 설정
            System.out.println("당신이 찾고자 하는 멤버의 고유 번호는 " + member.getUserId());
            return ResponseEntity.ok(member.getUserId());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        memberService.logoutMember(); // 세션에서 사용자 정보 제거
        httpSession.invalidate(); // 세션 무효화 (로그아웃)
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/users/current")
    public ResponseEntity<Map<String, Object>> checkLogin() {
        Long userId = (Long) httpSession.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        MemberResponseDTO curUser = memberService.findMemberByUserId(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("user", curUser);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userid}")
    public ResponseEntity<Map<String, MemberResponseDTO>> getMember(@PathVariable("userid") Long userId, HttpSession httpSession) {
        Map<String, MemberResponseDTO> map = new HashMap<>();
        MemberResponseDTO curUser = memberService.findMemberByUserId(userId);
        int followersCount = memberService.getFollowersCount(userId);
        int followingCount = memberService.getFollowingCount(userId);
        map.put("user", new MemberResponseDTO(curUser, followersCount, followingCount));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/users")
    public List<MemberResponseDTO> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/users/{userId}/liked-feeds")
    public ResponseEntity<Map<String, Object>> getLikedFeeds(@PathVariable("userId") Long userId,  HttpSession httpSession) {
        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        if(sessionUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다." );
        }
        List<Long> likedFeedIds = memberService.getLikedFeeds(sessionUserId);

        Map<String, Object> response = new HashMap<>();
        response.put("liked_feeds", likedFeedIds);

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/users/{userId}")
    public void updateMember(@PathVariable("userId") Long userId, @RequestBody MemberUpdateDTO dto, HttpSession httpSession) {
        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        if(sessionUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다." );
        }
        memberService.updateMember(sessionUserId, dto);
    }


    @DeleteMapping("/users/{userId}")
    public void deleteMember(@PathVariable("userId") Long userId, HttpSession httpSession) {
        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        if(sessionUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다." );
        }
        memberService.deleteMember(sessionUserId);
    }
}