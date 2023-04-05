package com.example.employez.restApi;

import com.example.employez.domain.entity_class.Course;
import com.example.employez.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CourseApi {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/course/byid")
    public Course getCourse() {
        return courseRepository.getCourseById(8325);
    }
}
