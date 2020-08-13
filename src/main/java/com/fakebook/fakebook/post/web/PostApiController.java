package com.fakebook.fakebook.post.web;

import com.fakebook.fakebook.post.service.PostService;
import com.fakebook.fakebook.post.web.dto.PostRegisterRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/update")
    public Long update(Long postId, @RequestBody PostRegisterRequestDto requestDto) {
        return postService.update(postId, requestDto);
    }

    @DeleteMapping("/delete")
    public HttpStatus delete(Long postId) {
        return postService.delete(postId);
    }
}
