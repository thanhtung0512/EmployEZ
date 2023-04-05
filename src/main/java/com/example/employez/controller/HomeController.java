package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.domain.entity_class.User;
import com.example.employez.dto.JobPostDto;
import com.example.employez.repository.EmployeeRepository;
import com.example.employez.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobPostDAO jobPostDAO;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SessionFactory sessionFactory;


    @Autowired
    private ModelMapper modelMapper;


    @GetMapping({"/homepage", "/"})
    public String index(Model model) {

        ArrayList<String> subField = new ArrayList<>();
        subField.add("Machine Learning");
        subField.add("DevOps");
        subField.add("Data Engineer");
        subField.add("Developer");
        subField.add("Software Engineer");
        subField.add("NLP Engineer");
        // subField.add("Researcher");

        Authentication auth = getAuth();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth",auth);
        model.addAttribute("mail", mail);


        System.out.println("authentication == null : " + auth == null);
        model.addAttribute("recentPosts", jobPostDAO.getJobPostingByNewestDate(false, 5));

        model.addAttribute("subField", subField);
        return "index";
    }


    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/search")
    /*@PreAuthorize("hasRole('ADMIN')")*/
    public String index(@RequestParam(name = "jobTitle", required = false, defaultValue = "") String jobTitle
            , @RequestParam(name = "location", required = false, defaultValue = "") String location
            , Model model) throws Exception {

        ArrayList<String> subField = new ArrayList<>();
        subField.add("Machine Learning");
        subField.add("DevOps");
        subField.add("Data Engineer");
        subField.add("Developer");
        subField.add("Software Engineer");
        subField.add("NLP Engineer");

        List<JobPostDto> jobPostDtos =  jobPostDAO.getBySalaryRange();
        List<JobPostDto> jobPostings = jobPostDAO.jobPostingListByTwoFields(jobTitle,location);

        Authentication auth = getAuth();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth",auth);
        model.addAttribute("mail", mail);
        List<JobPostDto> topFiveJobPost = new ArrayList<>();
        for (int i = 0; i < Math.min(5,jobPostDtos.size()); i++) {
            topFiveJobPost.add(jobPostDtos.get(i));
        }
        model.addAttribute("jobList", jobPostings);
        model.addAttribute("subField", subField);
        model.addAttribute("topFive", topFiveJobPost);
        model.addAttribute("jobTitleSearch", jobTitle);
        return "search";
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
        session.getTransaction().commit();
        session.close();
        return "single";
    }

    @GetMapping("/search/byskill/{skillName}")
    public String searchBySkill(@PathVariable(name = "skillName") String skillName, Model model) {
        ArrayList<JobPosting> jobPostingsBySkill = (ArrayList<JobPosting>) jobPostDAO.getBySkill(skillName);
        model.addAttribute("jobList", jobPostingsBySkill);
        model.addAttribute("skillName", skillName);
        return "search";
    }
}
