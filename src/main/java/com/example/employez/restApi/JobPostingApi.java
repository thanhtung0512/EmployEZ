package com.example.employez.restApi;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.JobPosting;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Transactional
public class JobPostingApi {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private JobPostDAO jobPostDAO;

    @GetMapping("/jobposts/{numbers}")
    public List<JobPosting> jobPostingList(@PathVariable int numbers) {
        List<JobPosting> jobPostings = new ArrayList<>();
        for (int i = 1; i <= numbers; i++) {
            jobPostings.add(jobPostDAO.jobPostingById(i));
        }
        return jobPostings;
    }

    @GetMapping("/jobposts/{title}/{location}")
    @Transactional
    public List<JobPosting> jobPostings(@PathVariable String title, @PathVariable String location) {
        return jobPostDAO.jobPostingListByTwoFields(title, location);
    }

}
