package com.example.employez.domain.entity_class;

 import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class JobSkillId implements Serializable {

    @Column(name = "fk_job")
    private Long fk_job;

    @Column(name = "fk_skill")
    private Long fk_skill;

    // getters and setters

    public Long getFk_job() {
        return fk_job;
    }

    public void setFk_job(Long fk_job) {
        this.fk_job = fk_job;
    }

    public Long getFk_skill() {
        return fk_skill;
    }

    public void setFk_skill(Long fk_skill) {
        this.fk_skill = fk_skill;
    }
}
