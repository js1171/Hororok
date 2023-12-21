package com.example.demo.controller;

import com.example.demo.dto.member.request.FeedRequestDTO;
import com.example.demo.dto.member.response.FeedResponseDTO;
import com.example.demo.entity.Feed;
import com.example.demo.service.FeedService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class FeedController {
    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService){
        this.feedService = feedService;
    }

    @PostMapping("/feeds")
    public ResponseEntity<FeedResponseDTO> createFeed(@RequestBody FeedRequestDTO requestDTO, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다.");
        }
        return ResponseEntity.ok(feedService.createFeed(requestDTO, httpSession));
    }

    @DeleteMapping("/feeds/{feedId}")
    public ResponseEntity<Void> deleteFeed(@PathVariable("feedId") Long feedId, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다.");
        }

        feedService.deleteFeed(feedId, userId);

        return ResponseEntity.noContent().build();
    }
}
