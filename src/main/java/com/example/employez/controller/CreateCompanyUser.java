package com.example.employez.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateCompanyUser {
    @GetMapping("/create_company_user")
    public String createCompanyUser() {
        return "error-404";
    }
}
