package com.fakebook.fakebook.post.web;

import com.fakebook.fakebook.post.service.PostService;
import com.fakebook.fakebook.post.web.dto.PostRegisterRequestDto;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/post")
@RestController
public class PostApiController {
    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/register")
    public Long register(@RequestBody PostRegisterRequestDto requestDto, Principal principal) {
        String userId = principal.getName();
        return postService.register(requestDto, userId);
    }

    @PostMapping("/update")
    public Long update(Long postId, @RequestBody PostRegisterRequestDto requestDto) {
        return postService.update(postId, requestDto);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        return postService.delete(id);
    }
}
