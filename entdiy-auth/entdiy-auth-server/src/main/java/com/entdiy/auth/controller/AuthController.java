package com.entdiy.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {
    
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "That's pretty basic!";
    }

}
