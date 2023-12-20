package com.example.demo.controller;

import com.example.demo.repository.FollowRepository;
import com.example.demo.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class FollowController {

    FollowRepository followRepository;
    private final FollowService followService;





}
