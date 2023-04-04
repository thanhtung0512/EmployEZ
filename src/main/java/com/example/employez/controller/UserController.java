package com.example.employez.controller;

import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.Role;
import com.example.employez.domain.entity_class.User;
import com.example.employez.domain.enumPackage.ROLE;
import com.example.employez.dto.UserDto;
import com.example.employez.repository.CompanyRepository;
import com.example.employez.repository.EmployeeRepository;
import com.example.employez.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final String ROLE_Company = "ROLE_Company";
    private static final String ROLE_Employee = "ROLE_Employee";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;


    private List<String> getUserRole(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return roles;
    }

    // show profile
    @GetMapping("/user_profile")
    @Transactional
    public String showProfile(Model model) {
        Session session = sessionFactory.openSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.toString());
        String mail = authentication.getName();
        User user = userRepository.getUserByEmail(mail);
        Integer userId = user.getId();
        Set<Role> roleSet = user.getRoles();
        UserDto userDto = new UserDto();
        if (roleSet.size() == 1) {
            for (Role role : roleSet) {
                if (role.getName().equals(ROLE.Employee)) {
                    String sql = "SELECT e.id FROM employee e  WHERE e.user_id = :userId";
                    Long employeeId = session.createNativeQuery(sql, Long.class)
                            .setParameter("userId", userId).getSingleResult();
                    Employee employee = employeeRepository.getEmployeeById(employeeId);
                    userDto.setFirstName(employee.getFirstName());
                    userDto.setLastName(employee.getLastName());
                    userDto.setFullName(employee.getFirstName() + " " + employee.getLastName());
                    userDto.setEmail(user.getEmail());
                    userDto.setJobTitle(employee.getJobTitle());
                    userDto.setAddress(employee.getCity() + "," + employee.getCountry());
                    userDto.setPhone(employee.getPhone());
                    userDto.setUniversity(employee.getUniversity());
                    userDto.setCountry(employee.getCountry());
                    userDto.setState(employee.getCity());
                } else if (role.getName().equals(ROLE.Company)) {
                    String sql = "SELECT c.id FROM company c  WHERE c.user_id = :userId";
                    Long companyId = session.createNativeQuery(sql, Long.class)
                            .setParameter("userId", userId)
                            .getSingleResult();
                    Company company = companyRepository.findCompanyById(companyId);
                    userDto.setFullName(company.getName());
                    userDto.setEmail(user.getEmail());
                    userDto.setJobTitle(null);
                    userDto.setAddress("TEST ADDRESS");
                    userDto.setPhone("TEST PHONE NUMBER");

                }
            }
        }
        model.addAttribute("user", userDto);
        model.addAttribute("roles", getUserRole(authentication));

        return "user_profile";
    }

    // edit profile
    @GetMapping("/edit_profile")
    public String editProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = getUserRole(authentication);
        model.addAttribute("roles", roles);
        model.addAttribute("user", new UserDto());

        return "edit_profile";
    }


    // save profile
    @PostMapping("/user_profile")
    @Transactional
    public String editProfile(@ModelAttribute(name = "user") UserDto userDto, Model model) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = getUserRole(authentication);
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        int userId = user.getId();
        System.out.println(userDto.toString());
        for (String role : roles) {
            System.out.println("ROLE: " + role);
            if (role.equals(ROLE_Company)) {
                // get the company id, update on this company
                Company company = session.createQuery("SELECT c FROM Company c where c.user.id = " + userId, Company.class)
                        .getSingleResult();
                String hql = "UPDATE Company SET name = :name WHERE id = :id";
                Integer execUpdate = session.createQuery(hql)
                        .setParameter("name", userDto.getFullName())
                        .setParameter("id", company.getId())
                        .executeUpdate();
                System.out.println("EXECUTE UPDATE = " + execUpdate);

            } else if (role.equals(ROLE_Employee)) {
                Long id = session.createQuery("SELECT e FROM Employee e WHERE e.user.id = " + userId, Employee.class)
                        .getSingleResult()
                        .getId();

                String hql = "UPDATE Employee SET firstName = :firstName, lastName = :lastName, jobTitle = :jobTitle, city = :city, university = :university, country = :country WHERE id = :id ";
                int execUpdate = session.createQuery(hql)
                        .setParameter("firstName", userDto.getFirstName())
                        .setParameter("lastName", userDto.getLastName())
                        .setParameter("jobTitle", userDto.getJobTitle())
                        .setParameter("city", userDto.getState())
                        .setParameter("university", userDto.getUniversity())
                        .setParameter("country", userDto.getCountry())
                        .setParameter("id",id).executeUpdate();

                System.out.println("EXECUTE UPDATE = " + execUpdate);
            }
        }

        model.addAttribute("user", userDto);
        model.addAttribute("roles",roles);
        session.getTransaction().commit();
        session.close();
        return "user_profile";
    }
}
