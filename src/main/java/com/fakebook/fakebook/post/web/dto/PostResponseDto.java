package com.fakebook.fakebook.post.web.dto;

import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.post.domain.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;

    private String content;

    private Member member;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.member = post.getMember();
    }
}
