package com.fakebook.fakebook.mypage.service;

import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.exception.DoesNotExistingUserIdException;
import com.fakebook.fakebook.member.web.dto.MemberResponseDto;
import com.fakebook.fakebook.post.domain.Post;
import com.fakebook.fakebook.post.domain.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class MyPageService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public MyPageService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public List<Post> getMyPagePosts(HttpSession session) {
        MemberResponseDto responseDto = (MemberResponseDto) session.getAttribute("user");
        Member memberById = memberRepository.findByUserId(responseDto.getUserId())
                .orElseThrow(() -> new DoesNotExistingUserIdException(responseDto.getUserId()));
        return postRepository.findAllByMember(memberById);
    }
}
