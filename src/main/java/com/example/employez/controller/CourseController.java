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


    private List<Course> getCacheCourseByName(String courseName) throws JsonProcessingException {
        List<Course> courses = new ArrayList<>();
        Optional<CacheData> optionalCacheData = cacheDataRepository.findById(courseName);
        if (optionalCacheData.isPresent()) {
            System.out.println("GET FROM REDIS CACHE " + courseName);
            String courseAsString = optionalCacheData.get().getValue();
            TypeReference<List<Course>> mapType = new TypeReference<>() {
            };
            courses = objectMapper.readValue(courseAsString, mapType);
            return courses;
        }
        courses = courseRepository.findCourseByTitleContaining(courseName);
        String productsAsJsonString = objectMapper.writeValueAsString(courses);
        CacheData cacheData = new CacheData(courseName, productsAsJsonString);
        cacheDataRepository.save(cacheData);

        return courses;
    }


    @GetMapping("/list/byname/{courseName}")
    public String courses(@PathVariable(name = "courseName") String courseName, Model model) throws JsonProcessingException {

        Session session = sessionFactory.openSession();
        session.beginTransaction();


        // get Course from Cache
        List<Course> courses = getCacheCourseByName(courseName);

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

        session.getTransaction().commit();
        session.close();
        return "course";
    }


    @GetMapping("/list/suggest")
    public String suggest(Model model) throws JsonProcessingException {

        List<Course> finalList = new ArrayList<>();


        Authentication auth = getAuth();
        User user = currentUserUtil.getCurrentUser();
        Integer userId = user.getId();
        Set<Role> roleSet = user.getRoles();

        List<Course> resultCourse = new ArrayList<>();


        if (roleSet.size() == 1) {
            for (Role role : roleSet) {
                if (role.getName().equals(ROLE.Employee)) {
                    Set<Skill> skills = currentUserUtil.employee(userId).getSkills();
                    for (Skill skill : skills) {

                        String skillName = skill.getName();
                        List<Course> courses = new ArrayList<>();
                        courses = getCacheCourseByName(skillName);


                        /*System.out.println("skillname" + skill.getName());
                        finalList.addAll(courseRepository.findCourseByTitleContaining(skill.getName()));*/
                        resultCourse.addAll(courses);
                    }
                }
            }
        }

        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("courses", resultCourse);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("courseName", "you");
        model.addAttribute("roles",authenticationUtil.getUserRole(authenticationUtil.authentication()) );
        return "course";
    }

}
