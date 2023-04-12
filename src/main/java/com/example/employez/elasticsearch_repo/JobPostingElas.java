package com.example.employez.elasticsearch_repo;

import com.example.employez.domain.entity_class.JobPosting;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingElas extends ElasticsearchRepository<JobPosting, Integer> {
    JobPosting findById(Integer id);
}
