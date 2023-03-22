package com.example.employez.restApi;


import co.elastic.clients.elasticsearch.ml.Job;
import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.JobPosting;
import com.example.employez.domain.enumPackage.EmploymentType;
import com.example.employez.domain.enumPackage.ProjectLocation;
import com.example.employez.util.HibernateUtil;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class JobPostingApi {

    @Autowired
    private JobPostDAO jobPostDAO;

    @GetMapping("/jobposts/{numbers}")
    @Transactional
    public List<JobPosting> jobPostingList(@PathVariable int numbers) {
        return jobPostDAO.jobPostingList(numbers);
    }

}
