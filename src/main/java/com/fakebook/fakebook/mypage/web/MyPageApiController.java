package com.fakebook.fakebook.mypage.web;

import com.fakebook.fakebook.mypage.service.MyPageService;
import com.fakebook.fakebook.post.web.dto.PostResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequestMapping("/mypage")
@RestController
public class MyPageApiController {
    private final MyPageService myPageService;

    public MyPageApiController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getMyPagePosts(Principal principal) {
        String userId = principal.getName();
        return myPageService.getMyPagePosts(userId);
    }
}
