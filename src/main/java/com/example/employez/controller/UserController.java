package com.example.employez.controller;

import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.Role;
import com.example.employez.domain.entity_class.User;
import com.example.employez.domain.enumPackage.ROLE;
import com.example.employez.repository.CompanyRepository;
import com.example.employez.repository.EmployeeRepository;
import com.example.employez.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;

    // show profile
    @GetMapping("/user/user_profile")
    @Transactional
    public String showProfile(Model model) {
        Session session = sessionFactory.openSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String mail = authentication.getName();
        User user = userRepository.getUserByEmail(mail);
        Integer userId = user.getId();
        Set<Role> roleSet = user.getRoles();
        if (roleSet.size() == 1) {
            for(Role role : roleSet) {
                if (role.getName().equals(ROLE.Employee)) {
                    Employee employee = employeeRepository.getEmployeeByUserId(userId);
                    model.addAttribute("employee",employee);
                }
                else if(role.getName().equals(ROLE.Company)) {
                    String sql = "SELECT c.id FROM company c INNER JOIN user u ON c.user_id = u.id";
                    Long companyId = session.createNativeQuery(sql, Long.class).getSingleResult();
                    Company company = companyRepository.findCompanyById(companyId);
                    model.addAttribute("company",company);
                }
            }
        }
        return "user_profile";
    }

    // edit profile
    @GetMapping("/user/edit_profile")
    public String editProfile() {
        return "edit_profile";
    }
}
