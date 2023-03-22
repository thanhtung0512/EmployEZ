package com.example.employez.domain;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "employee")
public class Employee extends User {




    private String firstName;

    private String lastName;
    private int desiredSalary;
    private String city;
    private String state;
    private int zipcode;




    private int yearOfExperience;

    private String jobTitle;


    @OneToMany(mappedBy = "employee")
    private Set<Resume> resumes;


    @ManyToMany
    @JoinTable(name = "apply",
            joinColumns = { @JoinColumn(name = "fk_employee") },
            inverseJoinColumns = { @JoinColumn(name = "fk_jobpost") })
    private Set<JobPosting> jobPostings;

    @ManyToMany
    @JoinTable(name = "has",
            joinColumns = { @JoinColumn(name = "fk_employee") },
            inverseJoinColumns = { @JoinColumn(name = "fk_skill") })
    private Set<Skill> skills;

    public Employee() {

    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Employee(String passwordHash, String email, String firstName, String lastName, int desiredSalary, String city, String state, int zipcode, int yearOfExperience, String jobTitle, Set<Resume> resumes) {
        super(passwordHash, email);

        this.firstName = firstName;
        this.lastName = lastName;
        this.desiredSalary = desiredSalary;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.yearOfExperience = yearOfExperience;
        this.jobTitle = jobTitle;
        this.resumes = resumes;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDesiredSalary() {
        return desiredSalary;
    }

    public void setDesiredSalary(int desiredSalary) {
        this.desiredSalary = desiredSalary;
    }


    public int getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(int yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public Set<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(Set<Resume> resumes) {
        this.resumes = resumes;
    }
}
