package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Entity
@Table(name="member")
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String id;
    private String password;
    private String name;
    private String nickname;

    private LocalDate birth;
    private char gender;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "updateAt")
    private LocalDate updatedAt;

    public Member(String id, String password, String name, String nickname, LocalDate birth, char gender) {
    }
}
