package com.example.employez.controller;


import com.example.employez.domain.entity_class.*;
import com.example.employez.domain.enumPackage.ROLE;
import com.example.employez.repository.CacheDataRepository;
import com.example.employez.repository.CourseRepository;
import com.example.employez.util.AuthenticationUtil;
import com.example.employez.util.CurrentUserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CacheDataRepository cacheDataRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Autowired
    private SessionFactory sessionFactory;

    public CourseController() {
    }

    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/list/byname/{courseName}")
    public String courses(@PathVariable(name = "courseName") String courseName, Model model) throws JsonProcessingException {


        Session session = sessionFactory.openSession();
        session.beginTransaction();


        Optional<CacheData> optionalCacheData = cacheDataRepository.findById(courseName);
        if (optionalCacheData.isPresent()) {
            System.out.println("GO HERE");
            String courseAsString = optionalCacheData.get().getValue();
            TypeReference<List<Course>> mapType = new TypeReference<>() {
            };
            List<Course> courses = objectMapper.readValue(courseAsString, mapType);
            model.addAttribute("courses", courses);
        }
        else {
            List<Course> courses = courseRepository.findCourseByTitleContaining(courseName);
            String productsAsJsonString = objectMapper.writeValueAsString(courses);
            CacheData cacheData = new CacheData(courseName, productsAsJsonString);
            cacheDataRepository.save(cacheData);
            model.addAttribute("courses", courses);
        }

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

        session.getTransaction().commit();
        session.close();
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
