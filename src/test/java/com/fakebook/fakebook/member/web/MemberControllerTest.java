package com.fakebook.fakebook.member.web;

import com.fakebook.fakebook.member.service.MemberService;
import com.fakebook.fakebook.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mvc;

    @Test
    void 로그인페이지_호출_테스트() throws Exception {
        mvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("새 계정 만들기")));
    }
}