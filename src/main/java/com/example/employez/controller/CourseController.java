package com.example.employez.controller;

import com.example.employez.domain.entity_class.Course;
import com.example.employez.domain.entity_class.Skill;
import com.example.employez.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/list")
    public String courses(Model model) {
        Course course = courseRepository.getCourseById(8325);
        model.addAttribute("courses",course);
        return "course";
    }
}
