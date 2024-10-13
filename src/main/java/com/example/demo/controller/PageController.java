package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/api/login")
    public String loginPage() {
        return "forward:/login.html";
    }

    @GetMapping("/api/register")
    public String registerPage() {
        return "forward:/register.html";
    }

}
