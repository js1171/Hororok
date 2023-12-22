package com.example.demo.service;

import com.example.demo.entity.Follow;
import com.example.demo.entity.Member;
import com.example.demo.repository.FollowRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberService memberService;
    private final HttpSession httpSession;

    public void follow(Long fromUserId, Long toUserId) {
        Long loggedInUserId = (Long) httpSession.getAttribute("userId");
        if (loggedInUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다.");
        }

        Member fromUser = memberService.findMemberById(loggedInUserId);
        Member toUser = memberService.findMemberById(toUserId);

        Optional<Follow> existingFollow = followRepository.findByFromUserAndToUser(fromUser, toUser);
        if (existingFollow.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 해당 유저를 팔로우하고 있습니다.");
        }

        Follow follow = new Follow(fromUser, toUser);

        if (fromUser.equals(toUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User 본인을 follow 할 수 없습니다.");
        }

        followRepository.save(follow);
    }
}
