package com.example.demo.repository;

import com.example.demo.entity.Feed;
import com.example.demo.entity.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<FeedLike, Long> {
    Optional<FeedLike> findByMemberUserIdAndFeedFeedId(Long userId, Long feedId);

    List<FeedLike> findByMemberUserId(Long userId);
}
