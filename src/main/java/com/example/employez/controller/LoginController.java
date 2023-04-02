package com.example.employez.controller;

import com.example.employez.dao.UserDAO.UserDAO;
import com.example.employez.dao.employeeDAO.EmployeeDAO;
import com.example.employez.dto.LoginDto;
import com.example.employez.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserDAO userDAO;


    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeDAO employeeDAO;

    @GetMapping("/employee/login")
    public String employeeLogin(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "employee_login";
    }


    @GetMapping("/employer/login")
    public String companyLogin(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "employer_login";
    }

    // login for employee
    @PostMapping(value = "/handleLogin")
    public String handleEmployeeLogin(@ModelAttribute("loginDto") LoginDto loginDto) {
        System.out.println(loginDto.getEmail() + " , " + loginDto.getPassword());
        if (userService.existEmployee(loginDto.getEmail())) {
            if (new BCryptPasswordEncoder().matches(loginDto.getPassword()
                    , userDAO.getByMail(loginDto.getEmail()).getPasswordHash())) {
                return "employeeHandleLogin";
            } else {
                return "redirect:/employee/login";
            }
        }
        return "redirect:/employee/login";

    }

    // login for company
    @PostMapping(value = "/employerHandleLogin")
    public String handleCompanyLogin(@ModelAttribute("loginDto") LoginDto loginDto) {
        System.out.println(loginDto.getEmail() + " , " + loginDto.getPassword());
        if (userService.existCompany(loginDto.getEmail())) {
            if (new BCryptPasswordEncoder().matches(loginDto.getPassword()
                    , userDAO.getByMail(loginDto.getEmail()).getPasswordHash()))
            {
                return "employerHandleLogin";
            }
            else {
                return "redirect:/employer/login";
            }
        }
        return "redirect:/employer/login";

    }

}
