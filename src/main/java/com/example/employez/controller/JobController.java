package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.Course;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.domain.entity_class.User;
import com.example.employez.repository.CourseRepository;
import com.example.employez.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
public class JobController {

    @Autowired
    private CourseRepository courseRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private JobPostDAO jobPostDAO;

    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    @GetMapping("/jobs/{id}")
    public String getDetailJob(@PathVariable(name = "id") int jobId, Model model) {
        JobPosting jobPosting = jobPostDAO.getById(jobId).getKey();
        Set<String> skillSet = jobPostDAO.getById(jobId).getValue();
        Authentication auth = getAuth();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth",auth);
        model.addAttribute("mail", mail);
        model.addAttribute("jobPosting", jobPosting);
        model.addAttribute("skills", skillSet);

        List<Course> courses = courseRepository.findCourseByTitleContaining("Spring").subList(0,6);
        model.addAttribute("courses",courses);
        return "single";
    }

    @PostMapping("/jobs/{id}")
    @Transactional
    public String handleFavorJob(@PathVariable(name = "id") int jobId, Model model) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        JobPosting jobPosting = jobPostDAO.getById(jobId).getKey();
        Set<String> skillSet = jobPostDAO.getById(jobId).getValue();
        model.addAttribute("jobPosting", jobPosting);
        model.addAttribute("skills", skillSet);
        Authentication authentication = getAuth();
        String mail = authentication.getName();
        User user = userRepository.getUserByEmail(mail);
        Employee employee = session.createQuery("SELECT e FROM Employee e WHERE e.user.id = " + user.getId(), Employee.class)
                .getSingleResult();
        System.out.println(employee.toString() + " Employee ");

        System.out.println(" jobID = " + jobId);

        String sql = "INSERT INTO favor_job (fk_employee, fk_jobpost) value (:fk_emp, :fk_job)";
        Integer execUpdate = session.createNativeQuery(sql, Integer.class)
                .setParameter("fk_emp",employee.getId())
                .setParameter("fk_job",jobId)
                .executeUpdate();
        System.out.println("EXCEC UPDATE = " + execUpdate);
        List<Course> courses = courseRepository.findCourseByTitleContaining("Spring").subList(0,6);
        model.addAttribute("courses",courses);
        session.getTransaction().commit();
        session.close();
        return "single";
    }

}
