package com.fakebook.fakebook.post;

import com.fakebook.fakebook.member.domain.Gender;
import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.domain.Role;
import com.fakebook.fakebook.member.web.dto.MemberResponseDto;
import com.fakebook.fakebook.post.domain.PostRepository;
import com.fakebook.fakebook.post.exception.IllegalAccessToPostException;
import com.fakebook.fakebook.post.service.PostService;
import com.fakebook.fakebook.post.web.dto.PostRegisterRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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

    @AfterEach
    private void cleanUp() {
        postRepository.deleteAll();
    }

    @Test
    void post_등록_확인() {
        //given
        PostRegisterRequestDto requestDto = new PostRegisterRequestDto();
        requestDto.setContent("testContent");

        //when
        postService.register(requestDto, mockHttpSession);

        //then
        assertThat(postRepository.findAll().get(0).getContent())
                .isEqualTo("testContent");
    }

    @WithMockUser(username = "testId")
    @Test
    void post_수정_확인() {
        //given
        PostRegisterRequestDto postWaitingForUpdate = new PostRegisterRequestDto();
        postWaitingForUpdate.setContent("testContent");

        PostRegisterRequestDto postWithNewContent = new PostRegisterRequestDto();
        postWithNewContent.setContent("new Content");

        postService.register(postWaitingForUpdate, mockHttpSession);
        Long postId = postRepository.findAll().get(0).getId();

        //when
        postService.update(postId, postWithNewContent);

        //then
        assertThat(postRepository.findById(postId).get().getContent())
                .isEqualTo("new Content");
    }

    @WithMockUser(username = "invalidUserId")
    @Test
    void 잘못된_사용자가_게시물_수정_시도_시_예외_발생() {
        //given
        PostRegisterRequestDto postWaitingForUpdate = new PostRegisterRequestDto();
        postWaitingForUpdate.setContent("testContent");

        PostRegisterRequestDto postWithNewContent = new PostRegisterRequestDto();
        postWithNewContent.setContent("new Content");

        postService.register(postWaitingForUpdate, mockHttpSession);
        Long postId = postRepository.findAll().get(0).getId();

        //then
        assertThatExceptionOfType(IllegalAccessToPostException.class)
                .isThrownBy(() -> postService.update(postId, postWithNewContent));
    }

    @WithMockUser(username = "testId")
    @Test
    void post_삭제_확인() {
        //given
        PostRegisterRequestDto postWaitingForDelete = new PostRegisterRequestDto();
        postWaitingForDelete.setContent("be deleted soon");
        postService.register(postWaitingForDelete, mockHttpSession);
        Long postId = postRepository.findAll().get(0).getId();

        //when
        postService.delete(postId);

        //then
        assertThat(postRepository.findById(postId).isPresent()).isFalse();
    }

    @WithMockUser(username = "invalidUserId")
    @Test
    void 잘못된_사용자가_게시물_삭제_시도_시_예외_발생() {
        //given
        PostRegisterRequestDto postWaitingForDelete = new PostRegisterRequestDto();
        postWaitingForDelete.setContent("be deleted soon");
        postService.register(postWaitingForDelete, mockHttpSession);
        Long postId = postRepository.findAll().get(0).getId();

        //then
        assertThatExceptionOfType(IllegalAccessToPostException.class)
                .isThrownBy(() -> postService.delete(postId));
    }
}