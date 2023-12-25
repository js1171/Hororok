package com.example.demo.controller;

import com.example.demo.service.LikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/feeds/{feedId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> likeFeed(@PathVariable("feedId") Long feedId, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다.");
        }

        likeService.likeFeed(userId, feedId);

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noStore().noCache().mustRevalidate());
        headers.setPragma("no-cache");
        headers.setExpires(0L);

        return ResponseEntity.noContent().headers(headers).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlikeFeed(@PathVariable("feedId") Long feedId, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다.");
        }

        likeService.unlikeFeed(userId, feedId);

        return ResponseEntity.noContent().build();
    }
}