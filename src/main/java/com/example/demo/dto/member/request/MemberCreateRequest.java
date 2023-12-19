package com.example.demo.dto.member.request;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class MemberCreateRequest {

    private String id;
    private String password;
    private String name;
    private String nickname;
    private LocalDate birth;
    private char gender;
}
