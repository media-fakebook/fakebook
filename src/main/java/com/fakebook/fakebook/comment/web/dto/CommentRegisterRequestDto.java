package com.fakebook.fakebook.comment.web.dto;

import com.fakebook.fakebook.comment.domain.Comment;
import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.post.domain.Post;
import lombok.Setter;

@Setter
public class CommentRegisterRequestDto {
	private String content;

	public Comment toEntity(Member member, Post post) {
		return new Comment(content, member, post);
	}
}
