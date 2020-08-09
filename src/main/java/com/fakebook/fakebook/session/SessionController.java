package com.fakebook.fakebook.session;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SessionController {
    @GetMapping("/session/{key}")
    public Object getSessionAttribute(@PathVariable(name = "key")String key, HttpSession session) {
        return session.getAttribute(key);
    }
}
