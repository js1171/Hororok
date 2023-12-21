package com.example.demo.service;

import com.example.demo.dto.member.request.FeedRequestDTO;
import com.example.demo.dto.member.response.FeedResponseDTO;
import com.example.demo.entity.Feed;
import com.example.demo.entity.Member;
import com.example.demo.repository.FeedRepository;
import com.example.demo.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public FeedResponseDTO createFeed(FeedRequestDTO feedRequestDTO, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("User ID를 세션에서 찾을 수 없습니다.");
        }

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));

        Feed feed = new Feed(feedRequestDTO, member);
        Feed saveFeed = feedRepository.save(feed);

        return new FeedResponseDTO(saveFeed);
    }

    @Transactional
    public void deleteFeed(Long feedId, Long userId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        if (!userId.equals(feed.getMember().getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시글 삭제 권한이 없습니다.");
        }

        feedRepository.deleteByFeedIdAndMember_UserId(feedId, userId);
    }
}