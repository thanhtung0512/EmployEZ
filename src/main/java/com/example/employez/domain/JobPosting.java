package com.example.employez.domain;


import com.example.employez.domain.enumPackage.EmploymentType;
import com.example.employez.domain.enumPackage.ProjectLocation;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Set;


@Entity
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    private Company company;

    @ManyToMany(mappedBy = "jobPostings")
    private Set<Employee> employees;

    @ManyToMany
    @JoinTable(name = "job_required_skill",
            joinColumns = { @JoinColumn(name = "fk_job") },
            inverseJoinColumns = { @JoinColumn(name = "fk_skill") })
    private Set<Skill> skills;

    private String jobTitle;
    private String jobDescription;
    private ProjectLocation projectLocation;

    private EmploymentType employmentType;

    private String city;
    private String state;
    private int zipcode;
    private Date datePosted;
    private int minSalary;
    private int maxSalary;

    public JobPosting(int id, Company company, Set<Employee> employees, Set<Skill> skills, String jobTitle, String jobDescription, ProjectLocation projectLocation, EmploymentType employmentType, String city, String state, int zipcode, Date datePosted, int minSalary, int maxSalary) {
        this.id = id;
        this.company = company;
        this.employees = employees;
        this.skills = skills;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.projectLocation = projectLocation;
        this.employmentType = employmentType;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.datePosted = datePosted;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public JobPosting() {

    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public ProjectLocation getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(ProjectLocation projectLocation) {
        this.projectLocation = projectLocation;
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

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }
}
