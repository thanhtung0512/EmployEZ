package com.example.employez.domain.entity_class;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "desiredSalary")
    private int desiredSalary;

    @Column(name = "city")
    private String city;


    @Column(name = "university")
    private String university;

    @Column(name = "country")
    private String country;

    @Column(name = "zipcode")
    private int zipcode;


    @Column(name = "yearOfExperience")
    private int yearOfExperience;


    @Column(name = "jobTitle")
    private String jobTitle;

    @OneToMany(mappedBy = "employee")
    private Set<Resume> resumes;


    @Column(name = "phone")
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "apply",
            joinColumns = {@JoinColumn(name = "fk_employee")},
            inverseJoinColumns = {@JoinColumn(name = "fk_jobpost")})
    private Set<JobPosting> jobPostings;

    @ManyToMany
    @JoinTable(name = "has",
            joinColumns = {@JoinColumn(name = "fk_employee")},
            inverseJoinColumns = {@JoinColumn(name = "fk_skill")})
    private Set<Skill> skills;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
