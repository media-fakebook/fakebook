package com.fakebook.fakebook.comment.service;

import com.fakebook.fakebook.comment.domain.Comment;
import com.fakebook.fakebook.comment.domain.CommentRepository;
import com.fakebook.fakebook.comment.exception.IllegalAccessToCommentException;
import com.fakebook.fakebook.comment.web.dto.CommentRegisterRequestDto;
import com.fakebook.fakebook.member.domain.Gender;
import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.domain.Role;
import com.fakebook.fakebook.post.domain.Post;
import com.fakebook.fakebook.post.domain.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class CommentServiceTest {
	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PostRepository postRepository;

	private Member member;

	private Post post;

	@BeforeAll
	private void setup() {
		member = Member.builder()
				.userId("testId")
				.password("testPassword")
				.birthday(LocalDate.of(1995, 8, 22))
				.gender(Gender.MALE)
				.name("dongheon")
				.role(Role.USER)
				.build();
		member = memberRepository.save(member);

		post = new Post("testPost", member);
		post = postRepository.save(post);
	}

	@BeforeEach
	private void cleanUp() {
		commentRepository.deleteAll();
	}

	@AfterAll
	void tearDown() {
		memberRepository.deleteAll();
	}

	@WithMockUser(username = "testId")
	@Test
	void 게시글_댓글_작성_확인() {
		//given
		String content = "test comment";
		CommentRegisterRequestDto registerRequestDto = new CommentRegisterRequestDto();
		registerRequestDto.setContent(content);
		UserDetails loginUser = (UserDetails) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();

		//when
		commentService.register(registerRequestDto, loginUser.getUsername(), post.getId());

		//then
		Comment comment = commentRepository.findAll().get(0);
		assertThat(comment.getContent())
				.isEqualTo(content);
	}

	@WithMockUser(username = "testId")
	@Test
	void 게시글_댓글_삭제_확인() {
		//given
		String content = "test comment";
		CommentRegisterRequestDto registerRequestDto = new CommentRegisterRequestDto();
		registerRequestDto.setContent(content);
		UserDetails loginUser = (UserDetails) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		Long commentId = commentService.register(registerRequestDto, loginUser.getUsername(), post.getId());

		//when
		commentService.delete(commentId);

		//then
		assertThat(commentRepository.findAll().size())
				.isEqualTo(0);
	}

	@WithMockUser(username = "invalidUserId")
	@Test
	void 잘못된_사용자가_댓글_삭제_시도_시_예외_발생() {
		//given
		String content = "test comment";
		CommentRegisterRequestDto registerRequestDto = new CommentRegisterRequestDto();
		registerRequestDto.setContent(content);
		Long commentId = commentService.register(registerRequestDto, "testId", post.getId());

		//when & then
		assertThatExceptionOfType(IllegalAccessToCommentException.class)
				.isThrownBy(() -> commentService.delete(commentId));
	}
}