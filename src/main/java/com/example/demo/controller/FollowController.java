package com.example.demo.controller;

import com.example.demo.entity.Follow;
import com.example.demo.repository.FollowRepository;
import com.example.demo.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@AllArgsConstructor
public class FollowController {

    FollowRepository followRepository;
    private final FollowService followService;

//    @PostMapping("/users/{fromUserId}/follows/{toUserId}")
//    public ResponseEntity<String> follow(@RequestBody Map<Long, Long> params) {
//        Follow follow = followRepository.findById(params.get("fromUserId"), )
//    }





}
