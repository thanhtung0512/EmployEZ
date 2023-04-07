package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.User;
import com.example.employez.domain.enumPackage.ApplyingJobState;
import com.example.employez.dto.JobPostDto;
import com.example.employez.util.AuthenticationUtil;
import com.example.employez.util.CurrentUserUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CurrentUserUtil currentUserUtil;


    @Autowired
    private AuthenticationUtil authenticationUtil;
    @Autowired
    private JobPostDAO jobPostDAO;

    @GetMapping("/apply/{id}")
    @Transactional
    public String apply(@PathVariable(name = "id") Long jobId, Model model) {

        JobPostDto jobPostDto = jobPostDAO.getById(jobId);
        model.addAttribute("jobId", jobId);
        model.addAttribute("jobPost", jobPostDto);
        return "apply-form";
    }

    @PostMapping("/tracking_job/{id}")
    @Transactional
    public String tracking(@PathVariable(name = "id") Long jobId) {
        User user = currentUserUtil.getCurrentUser();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String insertEmployeeApplyJob = "INSERT INTO apply (fk_employee,fk_jobpost,status) " +
                "value (:employeeId,:jobPostId,:initialState)";
        session.createNativeQuery(insertEmployeeApplyJob)
                .setParameter("employeeId", user.getId())
                .setParameter("jobPostId", jobId)
                .setParameter("initialState", ApplyingJobState.APPLICATION_RECEIVED.toString())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return "redirect:/tracking_job";
    }

    @GetMapping("/tracking_job")
    @Transactional
    public String trackAllJob(Model model) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = currentUserUtil.getCurrentUser();
        String selectJobIdAppliedByUser = "SELECT fk_jobpost FROM apply WHERE fk_employee = " + user.getId();
        List<Integer> jobIds = session.createNativeQuery(selectJobIdAppliedByUser).getResultList();
        List<JobPostDto> jobPostDtos = new ArrayList<>();
        String getApplyStateSql = "SELECT status FROM apply WHERE fk_employee = :emp_id AND fk_jobpost = :jobId ";

        for (Integer id : jobIds) {
            JobPostDto each = jobPostDAO.getById(new Long(id));
            String status = session.createNativeQuery(getApplyStateSql, String.class)
                    .setParameter("emp_id", user.getId())
                    .setParameter("jobId", id).getSingleResult();
            each.setApplyingJobState(ApplyingJobState.valueOf(status));
            jobPostDtos.add(each);
        }
        model.addAttribute("jobList", jobPostDtos);
        session.getTransaction().commit();
        session.close();
        return "tracking-job";
    }
}
