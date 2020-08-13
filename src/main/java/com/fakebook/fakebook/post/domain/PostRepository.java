package com.fakebook.fakebook.post.domain;

import com.fakebook.fakebook.member.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"member"})
    List<Post> findAllByMember(Member member);
}
