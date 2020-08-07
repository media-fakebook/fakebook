package com.fakebook.fakebook.member.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository repository;

    @AfterEach
    private void cleanUp(){
        repository.deleteAll();
    }

    @Test
    void Member_등록_확인() {
        //given
        Member member = Member.builder()
                .name("dongheon")
                .userId("testId")
                .password("testPassword")
                .birthday(LocalDate.of(1995,8,22))
                .gender(Gender.MALE)
                .role(Role.USER)
                .build();

        //when
        repository.save(member);

        //then
        System.out.println(repository.findAll().get(0).getGender());
        assertThat(repository.findAll().get(0)).isEqualTo(member);
    }
}