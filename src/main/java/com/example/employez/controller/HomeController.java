package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.JobPosting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private JobPostDAO jobPostDAO;

    @GetMapping("/homepage")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String indexx() {
        return "index";
    }

    @GetMapping("/search")
    public String index(@RequestParam(name = "jobTitle", required = false, defaultValue = "") String jobTitle
            , @RequestParam(name = "location", required = false, defaultValue = "") String location
            , Model model) throws Exception {
        ArrayList<JobPosting> jobPostings = (ArrayList<JobPosting>) jobPostDAO.jobPostingListByTwoFields(jobTitle, location);
        model.addAttribute("jobList", jobPostings);
        model.addAttribute("jobTitleSearch",jobTitle);
        return "search";
    }


    @GetMapping("/jobs/{id}")
    public String getDetailJob(@PathVariable(name = "id") int jobId, Model model) {
        JobPosting jobPosting = jobPostDAO.jobPostingById(jobId);
        model.addAttribute("jobPosting", jobPosting);
        return "single";
    }
}
