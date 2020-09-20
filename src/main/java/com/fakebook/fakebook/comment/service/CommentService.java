package com.fakebook.fakebook.comment.service;

import com.fakebook.fakebook.comment.domain.Comment;
import com.fakebook.fakebook.comment.domain.CommentRepository;
import com.fakebook.fakebook.comment.exception.DoesNotExistingCommentException;
import com.fakebook.fakebook.comment.exception.IllegalAccessToCommentException;
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
		Member memberByUserId = memberRepository.findByUserId(userId)
				.orElseThrow(() -> new DoesNotExistingUserIdException(userId));
		Post postById = postRepository.findById(postId)
				.orElseThrow(() -> new DoesNotExistingPostException(postId));
		Comment comment = requestDto.toEntity(memberByUserId, postById);
		postById.addComment(comment);
		return commentRepository.save(comment).getId();
	}

	@Transactional
	public Long delete(Long commentId) {
		if (isIllegalAccessToComment(commentId)) {
			throw new IllegalAccessToCommentException();
		}
		Comment commentById = commentRepository.findById(commentId)
				.orElseThrow(() -> new DoesNotExistingCommentException(commentId));
		Post post = commentById.getPost();
		post.deleteComment(commentById);
		commentRepository.delete(commentById);
		return commentById.getId();
	}

	private boolean isIllegalAccessToComment(Long commentId) {
		UserDetails loginUser = (UserDetails) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		String userIdOfLoginUser = loginUser.getUsername();
		Comment commentById = commentRepository.findById(commentId)
				.orElseThrow(() -> new DoesNotExistingCommentException(commentId));
		return !commentById.getMember().getUserId().equals(userIdOfLoginUser);
	}
}
