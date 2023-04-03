package com.example.employez.controller;

import com.example.employez.dao.UserDAO.UserDAO;
import com.example.employez.dto.LoginDto;
import com.example.employez.repository.UserRepository;
import com.example.employez.service.UserDetailsServiceImpl;
import com.example.employez.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/employee/login")
    public String employeeLogin(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "employee_login";
    }

    @GetMapping("/employer/login")
    public String companyLogin(Model model) {
        System.out.println("GO HERE");
        model.addAttribute("loginDto", new LoginDto());
        return "employer_login";
    }

    // login for employee
    /*@PostMapping(value = "/handleLogin")
    public String handleEmployeeLogin(@ModelAttribute("loginDto") LoginDto loginDto) {
        System.out.println(loginDto.getEmail() + " , " + loginDto.getPassword());
        if (userService.existEmployee(loginDto.getEmail())) {
            String userPassword = userRepository.getUserByEmail(loginDto.getEmail()).getPasswordHash();
            if (passwordEncoder.matches(loginDto.getPassword()
                    , userPassword)) {
                return "employeeHandleLogin";
            } else {
                return "redirect:/employee/login";

            }
        }

        return "redirect:/employee/login";

    }*/


    /*@GetMapping("/login-test")
    public String loginTest(Model model) {
        model.addAttribute("loginDto",new LoginDto());

        return "employee_login";
    }*/

    @PostMapping(value = "/handleLogin")
    public String handleEmployeeLogin(@ModelAttribute("loginDto") LoginDto loginDto) {
        System.out.println(loginDto.getEmail() + " , " + loginDto.getPassword());
        if (userService.existEmployee(loginDto.getEmail())) {
            String userPassword = userRepository.getUserByEmail(loginDto.getEmail()).getPasswordHash();
            if (passwordEncoder.matches(loginDto.getPassword()
                    , userPassword)) {
                return "employeeHandleLogin";
            } else {
                return "redirect:/employee/login";
                /*return "employee_login";*/
            }
        }
        /*return "employee_login";*/
        return "redirect:/employee/login";

    }


    // login for company
    @PostMapping(value = "/employerHandleLogin")
    public String handleCompanyLogin(@ModelAttribute("loginDto") LoginDto loginDto) {
        System.out.println(loginDto.getEmail() + " , " + loginDto.getPassword());
        if (userService.existCompany(loginDto.getEmail())) {
            if (new BCryptPasswordEncoder().matches(loginDto.getPassword()
                    , userDAO.getByMail(loginDto.getEmail()).getPasswordHash())) {
                return "employerHandleLogin";
            }
            else {
                return "redirect:/employer/login";
            }
        }
        return "redirect:/employer/login";

    }

}
