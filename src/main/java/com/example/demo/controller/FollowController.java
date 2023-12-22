package com.example.demo.controller;

import com.example.demo.entity.Follow;
import com.example.demo.repository.FollowRepository;
import com.example.demo.service.FollowService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

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
}