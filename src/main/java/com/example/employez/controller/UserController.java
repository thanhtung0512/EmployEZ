package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.*;
import com.example.employez.domain.enumPackage.ROLE;
import com.example.employez.dto.UserDto;
import com.example.employez.repository.CompanyRepository;
import com.example.employez.repository.EmployeeRepository;
import com.example.employez.repository.JobPostingRepository;
import com.example.employez.repository.UserRepository;
import com.example.employez.util.AuthenticationUtil;
import com.example.employez.util.CurrentUserUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final String ROLE_Company = "ROLE_Company";
    private static final String ROLE_Employee = "ROLE_Employee";

    @Autowired
    private CurrentUserUtil currentUserUtil;


    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private JobPostDAO jobPostDAO;

    @Autowired
    private JobPostingRepository jobPostingRepository;




    // show profile
    @GetMapping("/user_profile")
    @Transactional
    public String showProfile(Model model) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = currentUserUtil.getCurrentUser();
        Integer userId = user.getId();
        Set<Role> roleSet = user.getRoles();
        UserDto userDto = new UserDto();
        Set<JobPosting> jobPostings = null;
        if (roleSet.size() == 1) {
            for (Role role : roleSet) {
                if (role.getName().equals(ROLE.Employee)) {
                    /*String sql = "SELECT e.id FROM employee e  WHERE e.user_id = :userId";
                    Long employeeId = session.createNativeQuery(sql, Long.class)
                            .setParameter("userId", userId).getSingleResult();*/
                    Employee employee = currentUserUtil.employee(userId);
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
                    jobPostings = employee.getFavoriteJob();
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
        session.getTransaction().commit();
        session.close();
        model.addAttribute("user", userDto);
        model.addAttribute("roles", authenticationUtil.getUserRole(authenticationUtil.authentication()));
        model.addAttribute("jobList", jobPostings);


        // authentication
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        return "user_profile";
    }

    // edit profile
    @GetMapping("/edit_profile")
    public String editProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authenticationUtil.getUserRole(authenticationUtil.authentication());
        model.addAttribute("roles", roles);
        model.addAttribute("user", new UserDto());
        model.addAttribute("currentEmployee", currentUserUtil.employee(currentUserUtil.getCurrentUser().getId()));


        // authentication
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));

        return "edit_profile";
    }


    // save profile
    @PostMapping("/user_profile")
    @Transactional
    public String editProfile(@ModelAttribute(name = "user") UserDto userDto, @RequestParam(name = "file") MultipartFile file, Model model) throws IOException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // handle file upload
        Path tempFile = Files.createTempFile("resume", ".pdf");
        Files.write(tempFile, file.getBytes());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authenticationUtil.getUserRole(authenticationUtil.authentication());
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        int userId = user.getId();
        Set<JobPosting> jobPostings = null;
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
                Employee employee = currentUserUtil.employee(userId);
                Long id = employee
                        .getId();
                jobPostings = employee.getFavoriteJob();
                String hql = "UPDATE employee SET firstName = :firstName, lastName = :lastName, jobTitle = :jobTitle, city = :city, university = :university, country = :country WHERE id = :id ";
                int execUpdate = session.createNativeQuery(hql, Employee.class)
                        .setParameter("firstName", userDto.getFirstName())
                        .setParameter("lastName", userDto.getLastName())
                        .setParameter("jobTitle", userDto.getJobTitle())
                        .setParameter("city", userDto.getState())
                        .setParameter("university", userDto.getUniversity())
                        .setParameter("country", userDto.getCountry())
                        .setParameter("id", id).executeUpdate();
                userDto.addResumePath(tempFile.toString());
                Resume resume = new Resume();
                resume.setResumePath(tempFile.toString());
                resume.setEmployee(employee);
                employee.getResumes().add(resume);
                session.save(resume);
                System.out.println("EXECUTE UPDATE = " + execUpdate);
            }
        }


        model.addAttribute("user", userDto);
        model.addAttribute("roles", roles);
        model.addAttribute("jobList", jobPostings);


        // authentication
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));

        session.getTransaction().commit();
        session.close();
        return "user_profile";
    }

    @PostMapping("/deletejob/{id}")
    public String delFavorJob(@PathVariable(name = "id") int jobId, Model model) {
        User user = currentUserUtil.getCurrentUser();
        Employee employee = currentUserUtil.employee(user.getId());
        employee.getFavoriteJob().removeIf(s -> s.getId() == jobId);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String delFavorJob = "DELETE FROM favor_job WHERE fk_employee = " + employee.getId() + " AND fk_jobpost = " + jobId;
        Integer excec = session.createNativeQuery(delFavorJob, Integer.class).executeUpdate();
        System.out.println("DELETE FAVOR JOB " + excec);

        Integer userId = user.getId();
        Set<Role> roleSet = user.getRoles();
        UserDto userDto = new UserDto();
        Set<JobPosting> jobPostings = null;
        if (roleSet.size() == 1) {
            for (Role role : roleSet) {
                if (role.getName().equals(ROLE.Employee)) {
                    /*String sql = "SELECT e.id FROM employee e  WHERE e.user_id = :userId";
                    Long employeeId = session.createNativeQuery(sql, Long.class)
                            .setParameter("userId", userId).getSingleResult();*/

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
                    jobPostings = employee.getFavoriteJob();
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

        session.getTransaction().commit();
        session.close();
        model.addAttribute("user", userDto);
        model.addAttribute("roles", authenticationUtil.getUserRole(authenticationUtil.authentication()));
        model.addAttribute("jobList", jobPostings);


        // authentication
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));

        return "user_profile";
    }


}
