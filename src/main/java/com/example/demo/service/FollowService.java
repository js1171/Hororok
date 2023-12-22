package com.example.demo.service;

import com.example.demo.entity.Follow;
import com.example.demo.entity.Member;
import com.example.demo.repository.FollowRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        Follow follow = new Follow(fromUser, toUser);

        if (fromUser.equals(toUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User 본인을 follow 할 수 없습니다.");
        }

        followRepository.save(follow);
    }

    public void unfollow(Long fromUserId, Long toUserId) {
        Long loggedInUserId = (Long) httpSession.getAttribute("userId");
        if (loggedInUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User가 로그인되어 있지 않습니다.");
        }

        if (!loggedInUserId.equals(fromUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "다른 사용자의 팔로우를 취소할 수 없습니다.");
        }

        Member fromUser = memberService.findMemberById(fromUserId);
        Member toUser = memberService.findMemberById(toUserId);

        Follow follow = followRepository.findByFromUserAndToUser(fromUser, toUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 팔로우 관계를 찾을 수 없습니다."));

        followRepository.delete(follow);
    }
}
