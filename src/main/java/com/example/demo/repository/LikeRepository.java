package com.example.demo.repository;

import com.example.demo.entity.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<FeedLike, Long> {
    Optional<FeedLike> findByMemberUserIdAndFeedFeedId(Long userId, Long feedId);
}
가