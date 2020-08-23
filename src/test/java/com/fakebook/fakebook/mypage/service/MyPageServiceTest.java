package com.fakebook.fakebook.mypage.service;

import com.fakebook.fakebook.member.domain.Gender;
import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.domain.Role;
import com.fakebook.fakebook.post.domain.PostRepository;
import com.fakebook.fakebook.post.service.PostService;
import com.fakebook.fakebook.post.web.dto.PostRegisterRequestDto;
import com.fakebook.fakebook.post.web.dto.PostResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class MyPageServiceTest {
    @Autowired
    private MyPageService myPageService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

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
    }

    @WithMockUser(username = "testId")
    @Test
    void 자신의_모든_포스트_조회_동작_확인() {
        //given
        PostRegisterRequestDto requestDto = new PostRegisterRequestDto();
        requestDto.setContent("testContent");

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        postService.register(requestDto, userDetails.getUsername());

        //when
        List<PostResponseDto> myPagePosts = myPageService.getMyPagePosts(userDetails.getUsername());

        //then
        assertThat(myPagePosts.get(0).getContent()).isEqualTo("testContent");
    }
}