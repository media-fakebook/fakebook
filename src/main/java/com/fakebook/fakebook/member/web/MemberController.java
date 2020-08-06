package com.fakebook.fakebook.member.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

    @GetMapping
    public String login(){
        return "login";
    }
}
