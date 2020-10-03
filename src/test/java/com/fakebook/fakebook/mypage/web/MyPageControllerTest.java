package com.fakebook.fakebook.mypage.web;

import com.fakebook.fakebook.comment.service.CommentService;
import com.fakebook.fakebook.member.service.MemberService;
import com.fakebook.fakebook.mypage.service.MyPageService;
import com.fakebook.fakebook.post.service.PostService;
import com.fakebook.fakebook.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
public class MyPageControllerTest {
    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MyPageService myPageService;

    @MockBean
    private PostService postService;

    @MockBean
    private CommentService commentService;

    @WithMockUser(username = "testId")
    @Test
    public void mypage_호출_동작_확인() throws Exception {
        mockMvc.perform(get("/mypage"))
                .andExpect(view().name("mypage"));
    }
}