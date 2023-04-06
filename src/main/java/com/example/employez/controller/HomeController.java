package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.Course;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.domain.entity_class.User;
import com.example.employez.dto.JobPostDto;
import com.example.employez.repository.CourseRepository;
import com.example.employez.repository.EmployeeRepository;
import com.example.employez.repository.UserRepository;
import com.example.employez.util.AuthenticationUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private JobPostDAO jobPostDAO;


    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseRepository courseRepository;


    @GetMapping({"/homepage", "/"})
    public String index(Model model) {

        ArrayList<String> subField = new ArrayList<>();
        subField.add("Machine Learning");
        subField.add("DevOps");
        subField.add("Data Engineer");
        subField.add("Developer");
        subField.add("Software Engineer");
        subField.add("NLP Engineer");
        // subField.add("Researcher");

        List<Course> courses = courseRepository.findCourseByTitleContaining("Spring").subList(0,6);
        model.addAttribute("courses",courses);

        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth",auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));


        System.out.println("authentication == null : " + auth == null);
        model.addAttribute("recentPosts", jobPostDAO.getJobPostingByNewestDate(false, 5));

        model.addAttribute("subField", subField);
        return "index";
    }


    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


}
