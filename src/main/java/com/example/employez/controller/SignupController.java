package com.example.employez.controller;

import com.example.employez.dao.companyDAO.CompanyDAO;
import com.example.employez.dao.employeeDAO.EmployeeDAO;
import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.Role;
import com.example.employez.domain.entity_class.User;
import com.example.employez.domain.enumPackage.ROLE;
import com.example.employez.dto.UserForm;
import com.example.employez.repository.CompanyRepository;
import com.example.employez.repository.EmployeeRepository;
import com.example.employez.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class SignupController {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/employee/signup")
    public String signUp(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "employee_signup";
    }

    @GetMapping("/employer/signup")
    public String employerSignup(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "employer_signup";
    }

    @PostMapping("/homepage")
    public String signupProcess(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult result,
                                @RequestParam(name = "last_name") String lastName,
                                @RequestParam(name = "first_name") String firstName) {
        if (result.hasErrors()) {
            return "employee_signup";
        }
        if ( !userRepository.existsByEmail(userForm.getEmail())) {
            Employee employee = new Employee();
            User user = new User();
            user.setEmail(userForm.getEmail());
            user.setPasswordHash(new BCryptPasswordEncoder().encode(userForm.getPassword()));
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(1L, ROLE.Employee));
            user.setRoles(roleSet);
            employee.setUser(user);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employeeDAO.save(employee);
            return "redirect:/homepage";
        }
        return "employee_signup";

    }

    @RequestMapping("/handleSignup")
    public String signupProcess(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult result,
                                @RequestParam(name = "company_name") String companyName
                                ) {
        if (result.hasErrors()) {
            return "employer_signup";
        }


        if (!userRepository.existsByEmail(userForm.getEmail()) && companyRepository.findCompanyByName(companyName) == null) {
            System.out.println("GO HEEERE");
            Company company = new Company();
            User user = new User();
            user.setEmail(userForm.getEmail());
            user.setPasswordHash(new BCryptPasswordEncoder().encode(userForm.getPassword()));
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(2L, ROLE.Company));
            user.setRoles(roleSet);
            company.setName(companyName);
            company.setUser(user);
            companyDAO.save(company);
            return "redirect:/homepage";
        }
        return "employer_signup";
    }


}
