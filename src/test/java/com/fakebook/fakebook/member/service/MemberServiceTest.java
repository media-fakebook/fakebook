package com.fakebook.fakebook.member.service;

import com.fakebook.fakebook.member.domain.Gender;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.exception.DuplicatedUserIdException;
import com.fakebook.fakebook.member.web.dto.MemberRegisterRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    private void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    public void 회원가입_동작_확인() throws Exception {
        //given
        MemberRegisterRequestDto registerRequestDto = new MemberRegisterRequestDto("testId", "testPassword",
                "dongheon", 1995, 8, 22, Gender.MALE);

        //when
        memberService.register(registerRequestDto);

        //then
        assertThat(memberRepository.findAll().get(0))
                .isEqualTo(registerRequestDto.toEntity());
    }

    @Test
    public void 회원가입시_중복아이디_예외발생() throws NestedServletException {
        //given
        MemberRegisterRequestDto existingMember = new MemberRegisterRequestDto("testId", "testPassword",
                "dongheon", 1995, 8, 22, Gender.MALE);

        MemberRegisterRequestDto newMember = new MemberRegisterRequestDto("testId", "testPassword2",
                "dowon", 1994, 8, 22, Gender.FEMALE);

        memberService.register(existingMember);

        //then
        assertThatExceptionOfType(DuplicatedUserIdException.class)
                .isThrownBy(() -> memberService.register(newMember));
    }

    @Test
    void 회원가입시_패스워드_암호화_동작_확인() throws NestedServletException {
        //given
        String rawPassword = "testPassword";

        MemberRegisterRequestDto registerRequestDto = new MemberRegisterRequestDto("testId", rawPassword,
                "dongheon", 1995, 8, 22, Gender.MALE);

        //when
        memberService.register(registerRequestDto);
        String encodedPassword = memberRepository.findByUserId("testId").get().getPassword();

        //then
        assertThat(rawPassword).isNotEqualTo(encodedPassword);
    }
}