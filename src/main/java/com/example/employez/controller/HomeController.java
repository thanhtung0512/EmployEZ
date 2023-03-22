package com.example.employez.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/login_page")
    public String login() {
        return "login_page";
    }

    @GetMapping("/homepage")
    public String index() {
        return "index";
    }
}
