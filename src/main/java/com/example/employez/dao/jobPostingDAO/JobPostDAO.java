package com.example.employez.dao.jobPostingDAO;

import com.example.employez.domain.entity_class.JobPosting;

import java.util.List;

public interface JobPostDAO {
    JobPosting createJobPosting();

    void deleteJobPosting(int id);

    List<JobPosting> getJobPostingByLocation(String... location);

    List<JobPosting> getJobPostingByNewestDate();

    List<JobPosting> getJobPostingByJobTitle(String jobTitle);

    List<JobPosting> jobPostingList(int numbers);

    List<JobPosting> jobPostingListByNameAreaField(String name, String area, String field);

    List<JobPosting> jobPostingListByTwoFields(String field1, String field2);

    JobPosting jobPostingById(int id);

}
