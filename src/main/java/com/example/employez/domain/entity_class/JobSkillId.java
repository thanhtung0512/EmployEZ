package com.example.employez.domain.entity_class;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSkillId implements Serializable {

    @Column(name = "fk_job")
    private Long fk_job;

    @Column(name = "fk_skill")
    private Long fk_skill;

    // getters and setters


}
