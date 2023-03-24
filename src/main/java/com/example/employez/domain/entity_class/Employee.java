package com.example.employez.domain.entity_class;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee extends User {

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "desiredSalary")
    private int desiredSalary;

    @Column(name = "city")
    private String city;


    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private int zipcode;



    @Column(name = "yearOfExperience")
    private int yearOfExperience;


    @Column(name = "jobTitle")
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

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", desiredSalary=" + desiredSalary +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode=" + zipcode +
                ", yearOfExperience=" + yearOfExperience +
                ", jobTitle='" + jobTitle + '\'' +
                ", resumes=" + resumes +
                ", jobPostings=" + jobPostings +
                ", skills=" + skills +
                '}';
    }
}
