package com.example.demo.repository;

import com.example.demo.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    void deleteByFeedIdAndMember_UserId(Long feedId, Long userId);
}
