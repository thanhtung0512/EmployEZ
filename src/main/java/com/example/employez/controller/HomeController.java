package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.domain.entity_class.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private JobPostDAO jobPostDAO;


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

        model.addAttribute("subField", subField);
        return "index";
    }


    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
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

        model.addAttribute("subField", subField);
        ArrayList<JobPosting> jobPostings = (ArrayList<JobPosting>) jobPostDAO.jobPostingListByTwoFields(jobTitle, location);

        model.addAttribute("jobList", jobPostings);
        ArrayList<JobPosting> topFiveJobPost = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            topFiveJobPost.add(jobPostings.get(i));
        }
        model.addAttribute("topFive", topFiveJobPost);
        model.addAttribute("jobTitleSearch", jobTitle);
        return "search";
    }


    @GetMapping("/jobs/{id}")
    public String getDetailJob(@PathVariable(name = "id") int jobId, Model model) {
 JobPosting jobPosting = jobPostDAO.getById(jobId);
        model.addAttribute("jobPosting", jobPosting);
        ArrayList<Skill> skillSet = jobPosting.getSkills();
        model.addAttribute("skills", skillSet);
        return "single";
    }
}
