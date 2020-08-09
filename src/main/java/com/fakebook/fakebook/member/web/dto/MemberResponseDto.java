package com.fakebook.fakebook.member.web.dto;

import com.fakebook.fakebook.member.domain.Gender;
import com.fakebook.fakebook.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MemberResponseDto {
    private String userId;

    private String name;

    private LocalDate birthday;

    private Gender gender;

    public MemberResponseDto(Member member) {
        this.userId = member.getUserId();
        this.name = member.getName();
        this.birthday = member.getBirthday();
        this.gender = member.getGender();
    }
}
