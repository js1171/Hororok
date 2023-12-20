package com.example.demo;

import com.example.demo.service.member.MemberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HororokApplication {

	public static void main(String[] args) {
		SpringApplication.run(HororokApplication.class, args);

	}

}
