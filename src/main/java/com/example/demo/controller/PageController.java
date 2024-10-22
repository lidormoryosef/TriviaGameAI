package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "forward:/loginPage.html";
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "forward:/registerPage.html";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/loginPage";
    }
    @GetMapping("/homePage")
    public String redirectToHome() {
        return "forward:/homePage.html";
    }
    @GetMapping("/historyPage")
    public String redirectToHistory() {
        return "forward:/historyPage.html";
    }

}
