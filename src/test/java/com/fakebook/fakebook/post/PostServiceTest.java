package com.fakebook.fakebook.post;

import com.fakebook.fakebook.member.domain.Gender;
import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.domain.Role;
import com.fakebook.fakebook.post.domain.PostRepository;
import com.fakebook.fakebook.post.exception.IllegalAccessToPostException;
import com.fakebook.fakebook.post.service.PostService;
import com.fakebook.fakebook.post.web.dto.PostRegisterRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private Member member;

    @BeforeAll
    private void setUp() {
        member = Member.builder()
                .userId("testId")
                .password("testPassword")
                .birthday(LocalDate.of(1995, 8, 22))
                .gender(Gender.MALE)
                .name("dongheon")
                .role(Role.USER)
                .build();
        memberRepository.save(member);
    }

    @AfterEach
    private void cleanUp() {
        postRepository.deleteAll();
    }

	@AfterAll
	void tearDown() {
		memberRepository.deleteAll();
	}

    @WithMockUser(username = "testId")
    @Test
    void 게시물_등록_확인() {
        //given
        PostRegisterRequestDto requestDto = new PostRegisterRequestDto();
        requestDto.setContent("testContent");

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        //when
        postService.register(requestDto, userDetails.getUsername());

        //then
        assertThat(postRepository.findAll().get(0).getContent())
                .isEqualTo("testContent");

    }

    @WithMockUser(username = "testId")
    @Test
    void 게시물_수정_확인() {
        //given
        PostRegisterRequestDto postWaitingForUpdate = new PostRegisterRequestDto();
        postWaitingForUpdate.setContent("testContent");

        PostRegisterRequestDto postWithNewContent = new PostRegisterRequestDto();
        postWithNewContent.setContent("new Content");

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        postService.register(postWaitingForUpdate, userDetails.getUsername());
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

        postService.register(postWaitingForUpdate, "testId");
        Long postId = postRepository.findAll().get(0).getId();

        //then
        assertThatExceptionOfType(IllegalAccessToPostException.class)
                .isThrownBy(() -> postService.update(postId, postWithNewContent));
    }

    @WithMockUser(username = "testId")
    @Test
    void 게시물_삭제_확인() {
        //given
        PostRegisterRequestDto postWaitingForDelete = new PostRegisterRequestDto();
        postWaitingForDelete.setContent("be deleted soon");

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String userId = userDetails.getUsername();

        postService.register(postWaitingForDelete, userId);
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

        postService.register(postWaitingForDelete, "testId");
        Long postId = postRepository.findAll().get(0).getId();

        //when & then
        assertThatExceptionOfType(IllegalAccessToPostException.class)
                .isThrownBy(() -> postService.delete(postId));
    }
}