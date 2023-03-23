package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.JobPosting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    @GetMapping ("/search")
    public String index(@RequestParam(name = "search",required = false,defaultValue = "") String search
    ,@RequestParam(name = "area",required = false,defaultValue = "Usa") String area
    ,@RequestParam(name = "sub_field",required = false,defaultValue = "Developer") String subField, Model model) {
        // need return a job post list
        ArrayList<JobPosting> jobPostings = (ArrayList<JobPosting>) jobPostDAO.jobPostingListByNameAreaField(search,area,subField);;
        model.addAttribute("jobList",jobPostings);
        return "search";
    }
}
