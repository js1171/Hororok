package com.example.demo.controller;
import com.example.demo.dto.member.request.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
@Controller
public class MemberController {
    @Autowired
    MemberRepository memberRepository;
    private final MemberService memberService;

    @Autowired
    private HttpSession httpSession;
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
            memberService.loginMember(member); // 세션에 사용자 정보 저장
            httpSession.setMaxInactiveInterval(5 * 60); // 세션의 최대 유지 시간을 5분으로 설정
            System.out.println("당신이 찾고자 하는 멤버의 고유 번호는 " + member.getUserId());
            return ResponseEntity.ok(member.getUserId());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/check-login")
    public ResponseEntity<String> checkLogin() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId != null) {
            return ResponseEntity.ok("User is logged in with ID: " + userId);
        } else {
            return ResponseEntity.ok("User is not logged in");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        memberService.logoutMember(); // 세션에서 사용자 정보 제거
        httpSession.invalidate(); // 세션 무효화 (로그아웃)
        return ResponseEntity.ok("Logout successful");
    }
}