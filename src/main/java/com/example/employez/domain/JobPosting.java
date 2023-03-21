package com.example.employez.domain;

import com.example.employez.domain.enumPackage.JobType;
import jakarta.persistence.*;

import java.sql.Date;


@Entity
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    private Company company;

    private String jobTitle;
    private String jobDescription;
    private JobType jobType;
    private String city;
    private String state;
    private int zipcode;
    private Date datePosted;
    private int minSalary;
    private int maxSalary;

    public JobPosting(int id, Company company, String jobTitle, String jobDescription, JobType jobType, String city, String state, int zipcode, Date datePosted, int minSalary, int maxSalary) {
        this.id = id;
        this.company = company;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobType = jobType;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.datePosted = datePosted;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
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

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
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
