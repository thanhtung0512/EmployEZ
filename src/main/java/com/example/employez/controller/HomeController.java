package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.dto.JobPostDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private JobPostDAO jobPostDAO;



    @Autowired
    private ModelMapper modelMapper;


    @GetMapping({"/homepage", "/"})
    public String index(Model model) {
        model.addAttribute("recentPosts", jobPostDAO.getJobPostingByNewestDate(false, 5));
        ArrayList<String> subField = new ArrayList<>();
        subField.add("Machine Learning");
        subField.add("DevOps");
        subField.add("Data Engineer");
        subField.add("Developer");
        subField.add("Software Engineer");
        subField.add("NLP Engineer");
        // subField.add("Researcher");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        System.out.println("authentication == null : " + auth == null);
        model.addAttribute("auth",auth);
        model.addAttribute("mail", mail);
        model.addAttribute("subField", subField);
        return "index";
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
        // subField.add("Researcher");


        ArrayList<JobPosting> jobPostDtos = (ArrayList<JobPosting>) jobPostDAO.jobPostingListByTwoFields(jobTitle,location);


        ArrayList<JobPosting> topFiveJobPost = new ArrayList<>();
        for (int i = 0; i < Math.min(5,jobPostDtos.size()); i++) {
            topFiveJobPost.add(jobPostDtos.get(i));
        }
        model.addAttribute("jobList", jobPostDtos);
        model.addAttribute("subField", subField);
        model.addAttribute("topFive", topFiveJobPost);
        model.addAttribute("jobTitleSearch", jobTitle);
        return "search";
    }


    @GetMapping("/jobs/{id}")
    public String getDetailJob(@PathVariable(name = "id") int jobId, Model model) {
        JobPosting jobPosting = jobPostDAO.getById(jobId).getKey();
        Set<String> skillSet = jobPostDAO.getById(jobId).getValue();
        model.addAttribute("jobPosting", jobPosting);
        model.addAttribute("skills", skillSet);
        return "single";
    }

    @GetMapping("/search/byskill/{skillName}")
    public String searchBySkill(@PathVariable(name = "skillName") String skillName,Model model) {
        ArrayList<JobPosting> jobPostingsBySkill = (ArrayList<JobPosting>) jobPostDAO.getBySkill(skillName);
        model.addAttribute("jobList", jobPostingsBySkill);
        model.addAttribute("skillName",skillName);
        return "search";
    }
}
