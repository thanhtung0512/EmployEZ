package com.example.employez.repository;

import com.example.employez.domain.entity_class.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    List<Course> findCourseByTitleContaining(String contain);

    List<Course> findAll();

    Course findCourseById(Integer id);

    Course getCourseById(Integer id);
}
