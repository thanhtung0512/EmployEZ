package com.example.employez.controller;

import com.example.employez.dao.companyDAO.CompanyDAO;
import com.example.employez.dao.employeeDAO.EmployeeDAO;
import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.User;
import com.example.employez.dto.UserForm;
import com.example.employez.util.HibernateUtil;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class SignupController {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private CompanyDAO companyDAO;

    @GetMapping("/employee/signup")
    public String signUp(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "employee_signup";
    }

    @GetMapping("/employer/signup")
    public String employerSignup(Model model) {
        model.addAttribute("userForm",new UserForm());
        return "employer_signup";
    }

    @PostMapping("/")
    public String signupProcess(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult result,
                                @RequestParam(name = "last_name") String lastName,
                                @RequestParam(name = "first_name") String firstName) {
        if (result.hasErrors()) {
            return "employee_signup";
        }
        Employee employee = new Employee();
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        employee.setUser(user);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employeeDAO.save(employee);
        return "redirect:/homepage";
    }

    @PostMapping("/handleSignup")
    public String signupProcess(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult result,
                                @RequestParam(name = "company_name") String companyName
                                ) {
        if (result.hasErrors()) {
            return "employer_signup";
        }
        Company company = new Company();
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        company.setName(companyName);
        company.setUser(user);
        companyDAO.save(company);
        return "redirect:/homepage";
    }


}
