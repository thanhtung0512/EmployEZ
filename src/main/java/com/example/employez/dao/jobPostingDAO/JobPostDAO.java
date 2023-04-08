package com.example.employez.dao.jobPostingDAO;

import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.dto.JobPostDto;
import com.example.employez.util.Pair;

import java.util.List;
import java.util.Set;

public interface JobPostDAO {
    JobPosting createJobPosting();

    void deleteJobPosting(int id);

    List<JobPosting> getJobPostingByLocation(String... location);

    List<JobPostDto> getJobPostingByNewestDate(boolean getFull, int num);

    List<JobPosting> getJobPostingByJobTitle(String jobTitle);

    List<JobPosting> jobPostingList(int numbers);

    List<JobPosting> jobPostingListByNameAreaField(String name, String area, String field);

    List<JobPostDto> jobPostingListByTwoFields(String field1, String field2);

    Pair<JobPosting, Set<String>> getById(int id);

    Pair<JobPostDto, Set<String>> getJobPostDtoById(int id);

    List<JobPostDto> getBySkill(int id);

     List<JobPostDto> getBySkill(String skillName);

    List<JobPostDto> getBySalaryRange();
     JobPostDto getById(Long jobId);

     List<JobPostDto> getByCompanyId(Long companyId);
}
