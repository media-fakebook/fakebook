package com.fakebook.fakebook.member.web.dto;

import com.fakebook.fakebook.member.domain.Gender;
import com.fakebook.fakebook.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class MemberRegisterRequestDto {
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
                .password(password)
                .name(name)
                .birthday(LocalDate.of(birthdayYear, birthdayMonth, birthdayDay))
                .gender(gender)
                .build();
    }
}
