package com.fakebook.fakebook.post.web;

import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRegisterRequestDto {
    private String content;

    public Post toEntity(Member member) {
        return new Post(content, member);
    }
}
