package com.fakebook.fakebook.member.web;

import com.fakebook.fakebook.member.service.MemberService;
import com.fakebook.fakebook.member.web.dto.MemberRegisterRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.NestedServletException;

@RequestMapping("/member")
@RestController
public class MemberApiController {
    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public Long register(@RequestBody MemberRegisterRequestDto requestDto) throws NestedServletException {
        return memberService.register(requestDto);
    }
}
