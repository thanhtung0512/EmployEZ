package com.example.employez.dao.jobPostingDAO;

import com.example.employez.domain.entity_class.JobPosting;

import java.util.List;

public interface JobPostDAO {
    JobPosting createJobPosting();
    void deleteJobPosting(int id);
    List<JobPosting> getJobPostingByLocation(String... location);
    List<JobPosting> getJobPostingByNewestDate();
    List<JobPosting> getJobPostingByJobTitle(String jobTitle );

    public List<JobPosting> jobPostingList(int numbers);
    public List<JobPosting> jobPostingListByNameAreaField(String name, String area, String field);
}
