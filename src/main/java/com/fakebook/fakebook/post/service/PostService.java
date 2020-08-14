package com.fakebook.fakebook.post.service;

import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.exception.DoesNotExistingUserIdException;
import com.fakebook.fakebook.member.web.dto.MemberResponseDto;
import com.fakebook.fakebook.post.domain.Post;
import com.fakebook.fakebook.post.domain.PostRepository;
import com.fakebook.fakebook.post.exception.DoesNotExistingPostException;
import com.fakebook.fakebook.post.web.dto.PostRegisterRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository repository, MemberRepository memberRepository) {
        this.postRepository = repository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long register(PostRegisterRequestDto registerRequestDto, HttpSession session) {
        MemberResponseDto user = (MemberResponseDto) session.getAttribute("user");
        Member memberByUserId = memberRepository.findById(user.getId())
                .orElseThrow(() -> new DoesNotExistingUserIdException(user.getUserId()));
        return postRepository.save(registerRequestDto.toEntity(memberByUserId)).getId();
    }

    @Transactional
    public Long update(Long postId, PostRegisterRequestDto requestDto) {
        Post postById = postRepository.findById(postId)
                .orElseThrow(() -> new DoesNotExistingPostException(postId));
        return postById.update(requestDto);
    }

    @Transactional
    public Long delete(Long postId) {
        Post postById = postRepository.findById(postId)
                .orElseThrow(() -> new DoesNotExistingPostException(postId));
        postRepository.delete(postById);
        return postId;
    }
}
