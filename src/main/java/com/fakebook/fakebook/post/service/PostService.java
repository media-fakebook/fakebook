package com.fakebook.fakebook.post.service;

import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.exception.DoesNotExistingUserIdException;
import com.fakebook.fakebook.post.domain.Post;
import com.fakebook.fakebook.post.domain.PostRepository;
import com.fakebook.fakebook.post.exception.DoesNotExistingPostException;
import com.fakebook.fakebook.post.exception.NotAuthorizedException;
import com.fakebook.fakebook.post.web.dto.PostRegisterRequestDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository repository, MemberRepository memberRepository) {
        this.postRepository = repository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long register(PostRegisterRequestDto registerRequestDto, String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new DoesNotExistingUserIdException(userId));
        return postRepository.save(registerRequestDto.toEntity(member)).getId();
    }

    @Transactional
    public Long update(Long postId, PostRegisterRequestDto requestDto) {
        if (isIllegalAccessToPost(postId)) {
            throw new NotAuthorizedException();
        }
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new DoesNotExistingPostException(postId));
        return post.update(requestDto);
    }

    @Transactional
    public Long delete(Long postId) {
        if (isIllegalAccessToPost(postId)) {
            throw new NotAuthorizedException();
        }
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new DoesNotExistingPostException(postId));
        postRepository.delete(post);
        return postId;
    }

    private boolean isIllegalAccessToPost(Long postId) {
        UserDetails loginUser = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String userIdOfLoginUser = loginUser.getUsername();

        Post targetPost = postRepository.findById(postId)
                .orElseThrow(() -> new DoesNotExistingPostException(postId));
        String userIdOfRealOwner = targetPost.getMember().getUserId();
        return !userIdOfLoginUser.equals(userIdOfRealOwner);
    }
}
