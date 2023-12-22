package com.example.demo.dto.member.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberDTO {

    private String id;
    private String password;
    private String name;
    private String nickname;
    private LocalDate birth;
    private char gender;

    public MemberDTO(String id, String password, String name, String nickname, LocalDate birth, char gender) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
    }

    public MemberDTO() {}
}