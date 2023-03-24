package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.domain.entity_class.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private JobPostDAO jobPostDAO;


    @GetMapping("/login_page")
    public String login() {
        return "login_page";
    }


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
            , @RequestParam(name = "location", required = false, defaultValue = "Usa") String location
            , Model model) {
        // field1 : job title, skill or company
        // field2 : city, state, zip or remote

        // need return a job post list
//        ArrayList<JobPosting> jobPostings = (ArrayList<JobPosting>) jobPostDAO.jobPostingListByNameAreaField(search,area);;
        ArrayList<JobPosting> jobPostings = (ArrayList<JobPosting>) jobPostDAO.jobPostingListByTwoFields(jobTitle, location);
        model.addAttribute("jobList", jobPostings);
        return "search";
    }



    @GetMapping("/jobs/{id}")
    public String getDetailJob(@PathVariable(name = "id") int jobId, Model model) {
        JobPosting jobPosting = jobPostDAO.jobPostingById(jobId);
        model.addAttribute("jobPosting", jobPosting);
        return "single";
    }
}
