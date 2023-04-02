package com.example.employez.repository;

import com.example.employez.domain.entity_class.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Integer> {
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    JobPosting findJobPostingById(int id);
}
