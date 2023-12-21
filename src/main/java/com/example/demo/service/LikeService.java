package com.example.demo.service;

import com.example.demo.entity.Feed;
import com.example.demo.entity.FeedLike;
import com.example.demo.entity.Member;
import com.example.demo.repository.FeedRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;

    @Transactional
    public void likeFeed(Long userId, Long feedId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));

        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        // 이미 좋아요를 눌렀는지 확인
        if (likeRepository.findByMemberUserIdAndFeedFeedId(userId, feedId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 누르셨습니다.");
        }

        FeedLike like = new FeedLike(member, feed);
        likeRepository.save(like);
    }

    @Transactional
    public void unlikeFeed(Long userId, Long feedId) {
        FeedLike like = likeRepository.findByMemberUserIdAndFeedFeedId(userId, feedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요를 찾을 수 없습니다."));

        likeRepository.delete(like);
    }
}