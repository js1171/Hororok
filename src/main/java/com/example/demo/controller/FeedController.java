package com.example.demo.controller;

import com.example.demo.dto.member.request.FeedRequestDTO;
import com.example.demo.dto.member.response.FeedResponseDTO;
import com.example.demo.service.FeedService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    @PostMapping("/feeds")
    public ResponseEntity<FeedResponseDTO> createFeed(@RequestBody FeedRequestDTO requestDTO, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다.");
        }
        return ResponseEntity.ok(feedService.createFeed(requestDTO, httpSession));
    }

    @GetMapping("/feeds")
    public ResponseEntity<Map<String, List<FeedResponseDTO>>> getFeeds(){
        Map<String, List<FeedResponseDTO>> response = new HashMap<>();
        List<FeedResponseDTO> feedList = feedService.getFeeds();
        response.put("feeds", feedList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feeds/{feedId}")
    public ResponseEntity<Map<String, FeedResponseDTO>> getFeed(@PathVariable("feedId") Long feedId) {
        FeedResponseDTO feedResponseDTO = feedService.getFeed(feedId);
        if (feedResponseDTO != null) {
            Map<String, FeedResponseDTO> response = new HashMap<>();
            response.put("feed", feedResponseDTO);
            return ResponseEntity.ok(response);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        }
    }

    @PatchMapping("/feeds/{feedId}")
    public ResponseEntity<FeedResponseDTO> updateFeed(
            @PathVariable("feedId") Long feedId,
            @RequestBody FeedRequestDTO requestDTO,
            HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다.");
        }

        FeedResponseDTO updatedFeed = feedService.updateFeed(feedId, requestDTO, userId);
        return ResponseEntity.ok(updatedFeed);
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

    @GetMapping("/users/{userId}/feeds")
    public ResponseEntity<Map<String, List<FeedResponseDTO>>> userFeedsList(@PathVariable("userId") Long userId) {
        Map<String, List<FeedResponseDTO>> response = new HashMap<>();
        List<FeedResponseDTO> feedList = feedService.userFeedsList(userId);
        response.put("feeds", feedList);
        return ResponseEntity.ok(response);
    }
}