package com.fakebook.fakebook.post.domain;

import com.fakebook.fakebook.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByMember(Member member);
}
