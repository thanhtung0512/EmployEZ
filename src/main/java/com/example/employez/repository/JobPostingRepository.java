package com.example.employez.repository;

import com.example.employez.domain.entity_class.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Integer> {
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    JobPosting findJobPostingById(int id);


    @Query("SELECT j FROM JobPosting j WHERE j.jobTitle LIKE %:jobTitle% AND j.city LIKE %:city% ")
    ArrayList<JobPosting> findJobPostingByJobTitleAndCityModify( @Param("jobTitle") String jobTitle
            , @Param("city") String city);

    JobPosting findJobPostingById(Integer id);
}
