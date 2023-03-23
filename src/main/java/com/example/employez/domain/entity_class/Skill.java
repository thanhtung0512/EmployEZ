package com.example.employez.domain.entity_class;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;


    @ManyToMany(mappedBy = "skills")
    private Set<Employee> employees;


    @ManyToMany(mappedBy = "skills")
    private Set<JobPosting> jobPostings;

    @ManyToMany
    @JoinTable(name = "covers",
            joinColumns = { @JoinColumn(name = "fk_skill") },
            inverseJoinColumns = { @JoinColumn(name = "fk_course") })
    private Set<Course> courses;

    public Skill(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Skill() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
