package com.example.demo.repository;

import com.example.demo.entity.Follow;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(Member fromUser, Member toUser);

    Optional<List<Follow>> findByToUser(Member user);

    Optional<List<Follow>> findByFromUser(Member user);
}
