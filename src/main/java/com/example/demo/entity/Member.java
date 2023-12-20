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
@Table(name="member")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name="userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name="id")
    private String id;

    @Column(name="pw")
    private String pw;

    private String name;
    private String nickname;
    private LocalDate birth;
    private char gender;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    public Member(String id, String pw, String name, String nickname, LocalDate birth, char gender) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
    }
}
