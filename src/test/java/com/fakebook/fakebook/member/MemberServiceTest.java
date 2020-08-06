package com.fakebook.fakebook.member;

import com.fakebook.fakebook.member.domain.Gender;
import com.fakebook.fakebook.member.web.dto.MemberRegisterRequestDto;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.exception.DuplicatedUserIdException;
import com.fakebook.fakebook.member.service.MemberService;
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
    private void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    public void 회원가입_동작_확인() throws Exception {
        //given
        MemberRegisterRequestDto registerRequestDto = new MemberRegisterRequestDto();
        registerRequestDto.setUserId("testId");
        registerRequestDto.setPassword("testPassword");
        registerRequestDto.setName("dongheon");
        registerRequestDto.setBirthdayYear(1995);
        registerRequestDto.setBirthdayMonth(8);
        registerRequestDto.setBirthdayDay(22);
        registerRequestDto.setGender(Gender.MALE);

        //when
        memberService.register(registerRequestDto);

        //then
        assertThat(memberRepository.findAll().get(0)).isEqualTo(registerRequestDto.toEntity());
    }

    @Test
    public void 회원가입시_중복아이디_예외발생() throws NestedServletException {
        //given
        MemberRegisterRequestDto existingMember = new MemberRegisterRequestDto();
        existingMember.setUserId("testId");
        existingMember.setPassword("testPassword");
        existingMember.setName("dongheon");
        existingMember.setBirthdayYear(1995);
        existingMember.setBirthdayMonth(8);
        existingMember.setBirthdayDay(22);
        existingMember.setGender(Gender.MALE);

        MemberRegisterRequestDto newMember = new MemberRegisterRequestDto();
        newMember.setUserId("testId");
        newMember.setPassword("anotherPassword");
        newMember.setName("dowon");
        newMember.setBirthdayYear(1994);
        newMember.setBirthdayMonth(8);
        newMember.setBirthdayDay(22);
        newMember.setGender(Gender.FEMALE);

        memberService.register(existingMember);

        //then
        assertThatExceptionOfType(DuplicatedUserIdException.class)
                .isThrownBy(() -> memberService.register(newMember));
    }
}