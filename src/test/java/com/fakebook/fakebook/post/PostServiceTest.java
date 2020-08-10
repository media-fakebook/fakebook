package com.fakebook.fakebook.post;

import com.fakebook.fakebook.member.domain.Gender;
import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.domain.Role;
import com.fakebook.fakebook.member.web.dto.MemberResponseDto;
import com.fakebook.fakebook.post.domain.PostRepository;
import com.fakebook.fakebook.post.web.PostRegisterRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    private MockHttpSession mockHttpSession;

    @BeforeAll
    private void setUp() {
        Member member = Member.builder()
                .userId("testId")
                .password("testPassword")
                .birthday(LocalDate.of(1995, 8, 22))
                .gender(Gender.MALE)
                .name("dongheon")
                .role(Role.USER)
                .build();
        memberRepository.save(member);

        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("user", memberResponseDto);
    }

    @Test
    void post_등록_확인() {
        //given
        PostRegisterRequestDto requestDto = new PostRegisterRequestDto();
        requestDto.setContent("testContent");

        //when
        postService.register(requestDto, mockHttpSession);

        //then
        assertThat(postRepository.findAll().get(0).getContent()).isEqualTo("testContent");
    }
}