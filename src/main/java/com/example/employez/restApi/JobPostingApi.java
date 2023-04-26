package com.example.employez.restApi;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.dto.JobPostDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
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
    private JobPostDAO jobPostDAO;

    @GetMapping("/jobposts/byid/{id}")
    public JobPosting getById(@PathVariable int id) {
        return jobPostDAO.getById(id).getKey();
        // return jobPostingRepository.findJobPostingById(id);
    }

    @GetMapping("/jobposts/{numbers}")
    public List<JobPosting> jobPostingList(@PathVariable int numbers) {
        List<JobPosting> jobPostings = new ArrayList<>();
        for (int i = 1; i <= numbers; i++) {
            jobPostings.add(jobPostDAO.getById(i).getKey());
        }
        return jobPostings;
    }

    @GetMapping("/jobposts/{title}/{location}")
    @Transactional
    public List<JobPostDto> jobPostings(@PathVariable String title, @PathVariable String location) {
        return jobPostDAO.jobPostingListByTwoFields(title, location);
    }


}
