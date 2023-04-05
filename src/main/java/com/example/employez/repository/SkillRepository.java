package com.example.employez.repository;

import com.example.employez.domain.entity_class.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Integer> {
    Skill findByName(String name);
    List<Skill> findAll();
}
