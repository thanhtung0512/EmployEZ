package com.example.employez.controller;

import com.example.employez.domain.entity_class.Course;
import com.example.employez.domain.entity_class.Skill;
import com.example.employez.repository.CourseRepository;
import com.example.employez.repository.SkillRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CreateCourseCoverSkill {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("/coursecoverskill")
    @Transactional
    public String test() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sql = "INSERT INTO covers (fk_skill,fk_course) value (:fk_skill, :fk_course)";
        List<Course> courseList = courseRepository.findAll();
        List<Skill> skills = skillRepository.findAll();
        for (Course course : courseList) {
            Integer courseId = course.getId();
            for (Skill skill : skills) {
                String skillName = skill.getName();
                Integer skillId = skill.getId();
                if (course.getTitle().toLowerCase().contains(skillName.toLowerCase())) {
                    Integer excecUpdate = session.createNativeQuery(sql,Integer.class)
                            .setParameter("fk_skill",skillId)
                            .setParameter("fk_course",courseId)
                            .executeUpdate();
                }
            }
        }
        session.getTransaction().commit();
        session.close();
        return "error-404";
    }
}
