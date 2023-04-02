package com.example.employez.domain.entity_class;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_required_skill")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
