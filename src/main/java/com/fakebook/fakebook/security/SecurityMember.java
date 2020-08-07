package com.fakebook.fakebook.security;

import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

public class SecurityMember extends User {
    private static final String ROLE_PREFIX = "ROLE_";

    public SecurityMember(Member member) {
        super(member.getUserId(),member.getPassword(), makeGrantedAuthority(member.getRole()));
    }

    private static List<GrantedAuthority> makeGrantedAuthority(Role role){
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX+role));
        return list;
    }
}
