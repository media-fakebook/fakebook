package com.fakebook.fakebook.member.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "gender")
    private Gender gender;

    @Builder
    public Member(String userId, String password, String name,
                  LocalDate birthday, Gender gender) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }
}
