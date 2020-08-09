package com.fakebook.fakebook.security;

import com.fakebook.fakebook.member.domain.MemberRepository;
import com.fakebook.fakebook.member.exception.DoesNotExistingUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId)
                .map(SecurityMember::new)
                .orElseThrow(() -> new DoesNotExistingUserIdException(userId));
    }
}
