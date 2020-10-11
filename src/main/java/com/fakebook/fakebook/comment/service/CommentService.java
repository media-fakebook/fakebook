package com.fakebook.fakebook.comment.service;

import com.fakebook.fakebook.comment.domain.Comment;
import com.fakebook.fakebook.comment.domain.CommentRepository;
import com.fakebook.fakebook.comment.exception.DoesNotExistingCommentException;
import com.fakebook.fakebook.comment.exception.NotAuthorizedException;
import com.fakebook.fakebook.comment.web.dto.CommentRegisterRequestDto;
import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.exception.DoesNotExistingUserIdException;
import com.fakebook.fakebook.post.domain.Post;
import com.fakebook.fakebook.post.domain.PostRepository;
import com.fakebook.fakebook.post.exception.DoesNotExistingPostException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;
	private final PostRepository postRepository;

	public CommentService(CommentRepository commentRepository, MemberRepository memberRepository,
						  PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.memberRepository = memberRepository;
		this.postRepository = postRepository;
	}


	@Transactional
	public Long register(CommentRegisterRequestDto requestDto,
						 String userId, Long postId) {
		Member member = memberRepository.findByUserId(userId)
				.orElseThrow(() -> new DoesNotExistingUserIdException(userId));
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new DoesNotExistingPostException(postId));
		Comment comment = requestDto.toEntity(member, post);
		post.addComment(comment);
		return commentRepository.save(comment).getId();
	}

	@Transactional
	public Long delete(Long commentId) {
		if (isIllegalAccessToComment(commentId)) {
			throw new NotAuthorizedException();
		}
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new DoesNotExistingCommentException(commentId));
		Post post = comment.getPost();
		post.deleteComment(comment);
		commentRepository.delete(comment);
		return comment.getId();
	}

	private boolean isIllegalAccessToComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new DoesNotExistingCommentException(commentId));
		UserDetails loginUser = (UserDetails) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		String userIdOfLoginUser = loginUser.getUsername();
		return !comment.getMember().getUserId().equals(userIdOfLoginUser);
	}
}
