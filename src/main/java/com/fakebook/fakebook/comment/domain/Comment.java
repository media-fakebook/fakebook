package com.fakebook.fakebook.comment.domain;

import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.post.domain.Post;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Comment {
	@Column(name = "comment_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content")
	private String content;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
	private Post post;

	public Comment(String content, Member member, Post post) {
		this.content = content;
		this.member = member;
		this.post = post;
	}
}
