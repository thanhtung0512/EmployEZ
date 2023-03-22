package com.example.employez.dao.jobPostingDAO;

import com.example.employez.domain.JobPosting;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class JobPostDAOImpl implements JobPostDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public JobPosting createJobPosting() {
        return new JobPosting();
    }

    @Override
    @Transactional
    public void deleteJobPosting(int id) {

    }

    @Override
    @Transactional
    public List<JobPosting> getJobPostingByLocation(String... location) {
        return null;
    }

    @Override
    @Transactional
    public List<JobPosting> getJobPostingByNewestDate() {
        return null;
    }

    @Override
    @Transactional
    public List<JobPosting> getJobPostingByJobTitle(String jobTitle) {
        return null;
    }
}
