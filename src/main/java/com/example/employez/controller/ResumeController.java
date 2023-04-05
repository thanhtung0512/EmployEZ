package com.example.employez.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/resume")
public class ResumeController {

    @GetMapping("/create")
    public String createResume() {
        return "create-resume";
    }

    @GetMapping("/view")
    public String viewResume() {
        return "my-resume";
    }
}
