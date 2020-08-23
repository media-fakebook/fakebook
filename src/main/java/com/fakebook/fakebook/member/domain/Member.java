package com.fakebook.fakebook.member.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode(exclude = {"id", "password"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Builder
    private Member(String userId, String password, String name,
                   LocalDate birthday, Gender gender, Role role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.role = role;
    }
}
