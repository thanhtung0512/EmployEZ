package com.example.employez.domain.entity_class;

import jakarta.persistence.*;

@Entity
@Table(name = "job_required_skill")
public class JobSkill {

    @EmbeddedId
    private JobSkillId id;

    @ManyToOne
    @MapsId("fk_job")
    @JoinColumn(name = "id")
    private JobPosting jobPosting;

    @ManyToOne
    @MapsId("idB")
    @JoinColumn(name = "id")
    private Skill skill;

    // getters and setters
}
