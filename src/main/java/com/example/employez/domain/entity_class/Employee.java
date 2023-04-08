package com.example.employez.domain.entity_class;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "employee")
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

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
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


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favor_job",
            joinColumns = {@JoinColumn(name = "fk_employee")},
            inverseJoinColumns = {@JoinColumn(name = "fk_jobpost")})
    private Set<JobPosting> favoriteJob;


    public Employee() {
    }


    public Employee(User user, String firstName, String lastName, int desiredSalary, String city, String university, String country, int zipcode, int yearOfExperience, String jobTitle, Set<Resume> resumes, String phone, Set<JobPosting> jobPostings, Set<Skill> skills, Long id, Set<JobPosting> favoriteJob) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.desiredSalary = desiredSalary;
        this.city = city;
        this.university = university;
        this.country = country;
        this.zipcode = zipcode;
        this.yearOfExperience = yearOfExperience;
        this.jobTitle = jobTitle;
        this.resumes = resumes;
        this.phone = phone;
        this.jobPostings = jobPostings;
        this.skills = skills;
        this.id = id;
        this.favoriteJob = favoriteJob;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public int getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(int yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Set<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(Set<Resume> resumes) {
        this.resumes = resumes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<JobPosting> getJobPostings() {
        return jobPostings;
    }

    public void setJobPostings(Set<JobPosting> jobPostings) {
        this.jobPostings = jobPostings;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<JobPosting> getFavoriteJob() {
        return favoriteJob;
    }

    public void setFavoriteJob(Set<JobPosting> favoriteJob) {
        this.favoriteJob = favoriteJob;
    }

    public void addNewResume(Resume resume) {
        resumes.add(resume);
    }

    public void addNewSkill(Skill skill) {
        skills.add(skill);
    }
}
