package com.fakebook.fakebook.mypage.web;

import com.fakebook.fakebook.mypage.service.MyPageService;
import com.fakebook.fakebook.post.domain.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/mypage")
@RestController
public class MyPageApiController {
    private final MyPageService myPageService;

    public MyPageApiController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/posts")
    public List<Post> getMyPagePosts(HttpSession session) {
        return myPageService.getMyPagePosts(session);
    }
}
