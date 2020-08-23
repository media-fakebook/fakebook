package com.fakebook.fakebook.mypage.service;

import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.exception.DoesNotExistingUserIdException;
import com.fakebook.fakebook.post.domain.PostRepository;
import com.fakebook.fakebook.post.web.dto.PostResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyPageService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public MyPageService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public List<PostResponseDto> getMyPagePosts(String userId) {
        Member memberById = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new DoesNotExistingUserIdException(userId));
        return postRepository.findAllByMember(memberById)
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
}
