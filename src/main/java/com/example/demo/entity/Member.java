package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class Member {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    private char gender;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Feed> feedList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<FeedLike> feedLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Follow> fromUserList = new ArrayList<>();

    @OneToMany(mappedBy = "toUser", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Follow> toUserList = new ArrayList<>();

    public Member(String id, String pw, String name, String nickname, LocalDate birth, char gender) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
    }

    public Member() {

    }

    public void updateMember(String pw, String name, String nickname, LocalDate birth, Character gender) {
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
    }

}