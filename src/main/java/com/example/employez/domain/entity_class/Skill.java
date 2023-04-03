package com.example.employez.domain.entity_class;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "skill")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;


    @ManyToMany(mappedBy = "skills")
    private Set<Employee> employees;


    @ManyToMany(fetch = FetchType.LAZY
            , mappedBy = "skills"
            , cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REFRESH})
    private Set<JobPosting> jobPostings;

    @ManyToMany
    @JoinTable(name = "covers",
            joinColumns = {@JoinColumn(name = "fk_skill")},
            inverseJoinColumns = {@JoinColumn(name = "fk_course")})
    private Set<Course> courses;


}
