package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.dto.JobPostDto;
import com.example.employez.repository.JobPostingRepository;
import com.example.employez.util.AuthenticationUtil;
import com.example.employez.util.CurrentUserUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// list job post : /company/jobs/curent
// edit job post : /company/jobs/edit

// del job post : /company/jobs/del/{id}

@Controller
@RequestMapping("/company")
public class CompanyController {


    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Autowired
    private JobPostDAO jobPostDAO;

    @Autowired
    private JobPostingRepository jobPostingRepository;


    @GetMapping("/jobs/curent")
    public String getCurrent(Model model) {
        int userId = currentUserUtil.getCurrentUser().getId();
        Company company = currentUserUtil.company(userId);
        List<JobPostDto> jobPostDtosByCompany = jobPostDAO.getByCompanyId(company.getId());
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth",auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        model.addAttribute("jobList", jobPostDtosByCompany);
        return "company_jobs";
    }

    @RequestMapping("/jobs/del/{id}")
    public String delPost(@PathVariable(name = "id") Long id,Model model) {
        Session session =  sessionFactory.openSession();
        session.beginTransaction();
        String delJobByIdSql = "DELETE JobPosting j WHERE j.id = " + id;
        session.createQuery(delJobByIdSql   ).executeUpdate();
        int userId = currentUserUtil.getCurrentUser().getId();
        Company company = currentUserUtil.company(userId);
        List<JobPostDto> jobPostDtosByCompany = jobPostDAO.getByCompanyId(company.getId());
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth",auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        model.addAttribute("jobList", jobPostDtosByCompany);
        session.getTransaction().commit();
        session.close();
        return "redirect:/jobs/current";
    }

}
