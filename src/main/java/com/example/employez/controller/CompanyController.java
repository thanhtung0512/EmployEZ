package com.example.employez.controller;

import com.example.employez.dao.employeeDAO.EmployeeDAO;
import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.Company;
import com.example.employez.dto.EmployeeDto;
import com.example.employez.dto.JobPostDto;
import com.example.employez.repository.EmployeeRepository;
import com.example.employez.repository.JobPostingRepository;
import com.example.employez.repository.ResumeRepository;
import com.example.employez.util.AuthenticationUtil;
import com.example.employez.util.CurrentUserUtil;
import com.example.employez.util.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// list job post : /company/jobs/current
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

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ResumeRepository resumeRepository;


    @GetMapping("/jobs/current")
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
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        model.addAttribute("jobList", jobPostDtosByCompany);
        session.getTransaction().commit();
        session.close();
        return "redirect:/company/jobs/current";
    }


    @GetMapping("/jobs/edit/{id}")
    public String editJobPost(@PathVariable(name = "id") Long jobId, Model model) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        JobPostDto currentJob = jobPostDAO.getById(jobId);
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        JobPostDto jobPostDto = new JobPostDto();
        jobPostDto.setId(Math.toIntExact(jobId));
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        model.addAttribute("currentJob", currentJob);
        System.out.println(currentJob);
        model.addAttribute("job", jobPostDto);
        model.addAttribute("id", jobId);
        session.getTransaction().commit();
        session.close();
        return "edit_job";
    }


    @PostMapping("/jobs/edit/done/{id}")
    public String doneEditting(@ModelAttribute(name = "job") JobPostDto jobPostDto
            , @RequestParam(name = "jobDescription") String jobDescription
            , @PathVariable(name = "id") Long jobId
            , Model model) {
        String updateJobSql = "UPDATE JobPosting j " +
                "SET j.jobTitle = :jobTitle" +
                ", j.jobDescription = :jobDescription" +
                ", j.projectLocation = :projectLocation" +
                ", j.country = :country, j.city = :city" +
                ", j.minSalary = :minSalary" +
                ", j.maxSalary = :maxSalary" +
                ", j.state = :state " +
                "WHERE j.id = :id";
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery(updateJobSql)
                .setParameter("jobTitle", jobPostDto.getJobTitle())
                .setParameter("jobDescription", jobDescription)
                .setParameter("projectLocation", jobPostDto.getProjectLocation())
                .setParameter("country", jobPostDto.getCountry())
                .setParameter("city", jobPostDto.getCity())
                .setParameter("minSalary", jobPostDto.getMinSalary())
                .setParameter("maxSalary", jobPostDto.getMaxSalary())
                .setParameter("state", jobPostDto.getState())
                .setParameter("id", jobId).executeUpdate();

        System.out.println("JOB DESCRIPTION" + jobDescription);

        int userId = currentUserUtil.getCurrentUser().getId();
        Company company = currentUserUtil.company(userId);
        List<JobPostDto> jobPostDtosByCompany = jobPostDAO.getByCompanyId(company.getId());
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        model.addAttribute("jobList", jobPostDtosByCompany);


        session.getTransaction().commit();
        session.close();
        return "redirect:/company/jobs/current";

    }


    @GetMapping("/jobs/applications/{id}")
    @Transactional
    public String applications(@PathVariable(name = "id") Long jobId, Model model) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String getEmployeeIdSql = "SELECT fk_employee " +
                "FROM apply " +
                "WHERE fk_jobpost = " + jobId;
        String getResumeIdSql = "SELECT id FROM resume " +
                "WHERE employee_id = :empId";
        List<Long> empIds = session.createNativeQuery(getEmployeeIdSql)
                .getResultList();

        String selectEmpFields = "SELECT e.id, e.firstName, e.lastName, e.country, e.jobTitle " +
                "FROM Employee e " +
                "WHERE e.id = :empId";

        List<Pair<EmployeeDto, List<Integer>>> resultSet = new ArrayList<>();

        for (Long eachEmpId : empIds) {
            System.out.println("EMP ID = " + eachEmpId);

            EmployeeDto employeeDto = new EmployeeDto();
            // get employeeDto
            Object[][] result = session.createQuery(selectEmpFields, Object[][].class)
                    .setParameter("empId", eachEmpId).getResultList().toArray(new Object[0][]);
            for (Object[] object : result) {
                employeeDto.setId((Long) object[0]);
                employeeDto.setFirstName((String) object[1]);
                employeeDto.setLastName((String) object[2]);
                employeeDto.setCountry((String) object[3]);
                employeeDto.setJobTitle((String) object[4]);
            }

            List<Integer> resumesIds = session.createNativeQuery(getResumeIdSql)
                    .setParameter("empId", eachEmpId).getResultList();

            Pair<EmployeeDto, List<Integer>> pair = new Pair<>();
            pair.setKey(employeeDto);
            pair.setValue(resumesIds);
            resultSet.add(pair);
        }
        session.getTransaction().commit();
        session.close();
        model.addAttribute("pairList", resultSet);

        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        return "employee_apply_job";

    }


}
