package com.fakebook.fakebook.peed.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PeedController {

    @GetMapping("/peed")
    public String peed(){
        return "peed";
    }
}
