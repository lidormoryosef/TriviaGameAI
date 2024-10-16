package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/api/loginPage")
    public String loginPage() {
        return "forward:/loginPage.html";
    }

    @GetMapping("/api/registerPage")
    public String registerPage() {
        return "forward:/registerPage.html";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/api/loginPage";
    }
    @GetMapping("/api/homePage")
    public String redirectToHome() {
        return "forward:/homePage.html";
    }
    @GetMapping("/api/historyPage")
    public String redirectToHistory() {
        return "forward:/historyPage.html";
    }

}
