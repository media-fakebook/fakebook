package com.fakebook.fakebook.post.web;

import com.fakebook.fakebook.post.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostApiController {
    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/register")
    public Long register(@RequestBody PostRegisterRequestDto requestDto, HttpSession session) {
        return postService.register(requestDto, session);
    }
}
