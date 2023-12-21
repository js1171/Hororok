package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsById(String id);
    Member findByIdAndPassword(String id, String password);

    Optional<Member> findFirstByUserId(Long userid);


}