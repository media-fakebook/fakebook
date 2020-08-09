package com.fakebook.fakebook.member.web.dto;

import com.fakebook.fakebook.member.domain.Gender;
import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class MemberRegisterRequestDto {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Role defaultRole = Role.USER;

    private String userId;

    private String password;

    private String name;

    private int birthdayYear;

    private int birthdayMonth;

    private int birthdayDay;

    private Gender gender;

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .birthday(LocalDate.of(birthdayYear, birthdayMonth, birthdayDay))
                .gender(gender)
                .role(defaultRole)
                .build();
    }
}
