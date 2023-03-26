package com.example.employez.restApi;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.JobPosting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/jobposts/{title}/{location}")
    @Transactional
    public List<JobPosting> jobPostings(@PathVariable String title, @PathVariable String location) {
        return jobPostDAO.jobPostingListByTwoFields(title,location);
    }

}
