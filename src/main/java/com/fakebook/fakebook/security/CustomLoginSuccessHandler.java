package com.fakebook.fakebook.security;

import com.fakebook.fakebook.member.domain.Member;
import com.fakebook.fakebook.member.service.MemberService;
import com.fakebook.fakebook.member.web.dto.MemberResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    MemberService memberService;

    public CustomLoginSuccessHandler(String nextUrl) {
        setDefaultTargetUrl(nextUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Member memberByUserId = memberService.findByUserId(userDetails.getUsername());
        session.setAttribute("user", new MemberResponseDto(memberByUserId));
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
