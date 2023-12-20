package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Member {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "id")
    private String id;

    @Column(name = "pw")
    private String password;

    private String name;
    private String nickname;
    private LocalDate birth;
    private char gender;


    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public Member(String id, String password, String name, String nickname, LocalDate birth, char gender) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
    }

}