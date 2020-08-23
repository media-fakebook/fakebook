package com.fakebook.fakebook.post.domain;

import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.post.web.dto.PostRegisterRequestDto;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EqualsAndHashCode
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    public Post(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    public Long update(PostRegisterRequestDto registerRequestDto) {
        this.content = registerRequestDto.getContent();
        return this.id;
    }
}
