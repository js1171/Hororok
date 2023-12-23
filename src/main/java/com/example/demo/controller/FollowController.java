package com.example.demo.controller;

import com.example.demo.dto.member.response.MemberResponseDTO;
import com.example.demo.service.FollowService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
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
    public ResponseEntity<List<MemberResponseDTO>> getFollowers(@PathVariable("userId") Long userId) {
        List<MemberResponseDTO> followers = followService.getFollowers(userId);
        Map<String, List<MemberResponseDTO>> response = Collections.singletonMap("followers", followers);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/users/{userId}/following")
    public ResponseEntity<List<MemberResponseDTO>> getFollowing(@PathVariable("userId") Long userId) {
        List<MemberResponseDTO> following = followService.getFollowing(userId);
        Map<String, List<MemberResponseDTO>> response = Collections.singletonMap("following", following);
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