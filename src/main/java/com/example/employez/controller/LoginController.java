package com.example.employez.controller;

import com.example.employez.dao.employeeDAO.EmployeeDAO;
import com.example.employez.domain.entity_class.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private EmployeeDAO employeeDAO;

    @GetMapping("/login-page")
    public String loginn() {
        return "login";
    }

    // login for employee
    @RequestMapping("/handleLogin")
    public String handleLogin(@RequestParam(name = "email") String email
            , @RequestParam(name = "password") String password) {
        Employee employee = employeeDAO.getByMail(email);
        if (employee == null) {
            System.out.println("Not yet registered");
        }
        else {
            if (new BCryptPasswordEncoder().matches(password,employee.getPasswordHash())){
                System.out.println("Login successfully");
            }
        }
        return "handleLogin";
    }

}
