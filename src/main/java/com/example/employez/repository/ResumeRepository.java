package com.example.employez.repository;

import com.example.employez.domain.entity_class.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    Resume findResumeById(Integer id);
}
