package com.example.employez.controller;

import com.example.employez.domain.entity_class.Course;
import com.example.employez.domain.entity_class.Skill;
import com.example.employez.repository.CourseRepository;
import com.example.employez.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/list/{courseName}")
    public String courses(@PathVariable(name = "courseName") String courseName, Model model) {
        List<Course> courses = courseRepository.findCourseByTitleContaining(courseName);
        model.addAttribute("courses",courses);
        Authentication auth = getAuth();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth",auth);
        model.addAttribute("mail", mail);
        model.addAttribute("courseName",courseName);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        return "course";
    }
}
