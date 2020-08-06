package com.fakebook.fakebook.member.service;

import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.web.dto.MemberRegisterRequestDto;
import com.fakebook.fakebook.member.exception.DuplicatedUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;


@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository repository;

    @Transactional
    public Long register(MemberRegisterRequestDto requestDto) throws NestedServletException {
        if (isExistingUserId(requestDto.getUserId())) {
            throw new DuplicatedUserIdException(requestDto.getUserId());
        }
        return repository.save(requestDto.toEntity()).getId();
    }

    private boolean isExistingUserId(String userId) {
        return repository.findByUserId(userId).isPresent();
    }
}
