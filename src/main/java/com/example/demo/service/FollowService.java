package com.example.demo.service;

import com.example.demo.dto.member.request.MemberDTO;
import com.example.demo.dto.member.response.MemberResponseDTO;
import com.example.demo.entity.Follow;
import com.example.demo.entity.Member;
import com.example.demo.repository.FollowRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberService memberService;
    private final HttpSession httpSession;

    @Transactional
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

    @Transactional
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

    @Transactional
    public List<MemberResponseDTO> getFollowers(Long userId) {
        Member user = memberService.findMemberById(userId);
        List<Follow> followerRelations = followRepository.findByToUser(user)
                .orElse(Collections.emptyList());
        return followerRelations.stream()
                .map(follow -> new MemberResponseDTO(follow.getFromUser()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MemberDTO> getFollowing(Long userId) {
        Member user = memberService.findMemberById(userId);
        List<Follow> followingRelations = followRepository.findByFromUser(user);
        return followingRelations.stream()
                .map(follow -> convertToMemberDTO(follow.getToUser()))
                .collect(Collectors.toList());
    }

    private MemberDTO convertToMemberDTO(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getPassword(),
                member.getName(),
                member.getNickname(),
                member.getBirth(),
                member.getGender()
        );
    }
}
