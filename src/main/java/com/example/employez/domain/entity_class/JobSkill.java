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


    public JobSkill(JobSkillId id, JobPosting jobPosting, Skill skill) {
        this.id = id;
        this.jobPosting = jobPosting;
        this.skill = skill;
    }

    public JobSkill() {

    }

    public JobSkillId getId() {
        return id;
    }

    public void setId(JobSkillId id) {
        this.id = id;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
