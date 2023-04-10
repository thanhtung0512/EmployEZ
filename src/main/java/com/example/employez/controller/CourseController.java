package com.example.employez.controller;

import com.example.employez.domain.entity_class.Course;
import com.example.employez.domain.entity_class.Role;
import com.example.employez.domain.entity_class.Skill;
import com.example.employez.domain.entity_class.User;
import com.example.employez.domain.enumPackage.ROLE;
import com.example.employez.repository.CourseRepository;
import com.example.employez.util.AuthenticationUtil;
import com.example.employez.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/list/byname/{courseName}")
    public String courses(@PathVariable(name = "courseName") String courseName, Model model) {
        List<Course> courses = courseRepository.findCourseByTitleContaining(courseName);
        model.addAttribute("courses", courses);
        Authentication auth = getAuth();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("courseName", courseName);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        return "course";
    }


    @GetMapping("/list/suggest")
    public String suggest(Model model) {

        List<Course> finalList = new ArrayList<>();


        Authentication auth = getAuth();
        User user = currentUserUtil.getCurrentUser();
        Integer userId = user.getId();
        Set<Role> roleSet = user.getRoles();

        if (roleSet.size() == 1) {
            for (Role role : roleSet) {
                if (role.getName().equals(ROLE.Employee)) {
                    Set<Skill> skills = currentUserUtil.employee(userId).getSkills();
                    for (Skill skill : skills) {
                        System.out.println("skillname" + skill.getName());
                        finalList.addAll(courseRepository.findCourseByTitleContaining(skill.getName()));
                    }
                }
            }
        }

        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("courses", finalList);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("courseName", "you");
        model.addAttribute("roles",authenticationUtil.getUserRole(authenticationUtil.authentication()) );
        return "course";
    }

}
