package com.example.demo.controller;

import com.example.demo.dto.member.request.MemberDTO;
import com.example.demo.service.FollowService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final HttpSession httpSession;

    @PostMapping("/users/{userId}/follows/{toUserId}")
    public ResponseEntity<Void> followUser(
            @PathVariable("userId") Long userId,
            @PathVariable("toUserId") Long toUserId) {
        Long fromUserId = (Long) httpSession.getAttribute("userId");
        if (fromUserId == null || !fromUserId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않거나 권한이 없습니다.");
        }

        followService.follow(fromUserId, toUserId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/followers")
    public ResponseEntity<List<MemberDTO>> getFollowers(@PathVariable("userId") Long userId) {
        List<MemberDTO> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/users/{userId}/following")
    public ResponseEntity<List<MemberDTO>> getFollowing(@PathVariable("userId") Long userId) {
        List<MemberDTO> following = followService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }

    @DeleteMapping("/users/{userId}/follows/{toUserId}")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable("userId") Long userId,
            @PathVariable("toUserId") Long toUserId) {
        Long fromUserId = (Long) httpSession.getAttribute("userId");
        if (fromUserId == null || !fromUserId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않거나 권한이 없습니다.");
        }

        followService.unfollow(fromUserId, toUserId);

        return ResponseEntity.noContent().build();
    }
}