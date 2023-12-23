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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<FeedResponseDTO> getFeeds() {
        List<Feed> feeds = feedRepository.findAllByOrderByCreatedAtDesc();
        return feeds.stream()
                .map(feed -> new FeedResponseDTO(feed, feed.getLikesCnt().size(), feed.getCommentList().size()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FeedResponseDTO getFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        return new FeedResponseDTO(feed, feed.getLikesCnt().size(), feed.getCommentList().size());
    }

    @Transactional
    public FeedResponseDTO updateFeed(Long feedId, FeedRequestDTO feedRequestDTO, Long userId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        // 게시글의 작성자와 현재 로그인한 사용자가 동일한지 확인
        if (!userId.equals(feed.getMember().getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시글 수정 권한이 없습니다.");
        }

        // 게시글 내용 업데이트
        if (feedRequestDTO != null && feedRequestDTO.getContents() != null) {
            feed.setContents(feedRequestDTO.getContents());
            feed.setUpdatedAt(LocalDateTime.now());
        }

        // 업데이트된 게시글 저장
        Feed updatedFeed = feedRepository.save(feed);

        return new FeedResponseDTO(updatedFeed);
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