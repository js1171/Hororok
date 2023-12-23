package com.example.demo.dto.member.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MemberRequestDTO {

    private String id;
    private String pw;
    private String name;
    private String nickname;
    private LocalDate birth;
    private char gender;

    public MemberRequestDTO(String id, String pw, String name, String nickname, LocalDate birth, char gender) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
    }

    public char getGender() {
        return gender;
    }
}