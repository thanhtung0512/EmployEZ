package com.example.employez.domain.entity_class;


import com.example.employez.domain.enumPackage.EmploymentType;
import com.example.employez.domain.enumPackage.ProjectLocation;
import jakarta.persistence.*;


import java.sql.Timestamp;
import java.util.Set;


@Entity
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @Column(name = "jobTitle")
    private String jobTitle;

    @Column(name = "jobDescription")
    private String jobDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "projectLocation")
    private ProjectLocation projectLocation;


    @Enumerated(EnumType.STRING)
    @Column(name = "employmentType")
    private EmploymentType employmentType;


    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;


    @Column(name = "country")
    private String country;

    @Column(name = "datePosted")
    private Timestamp datePosted;

    @Column(name = "minSalary")
    private int minSalary; //

    @Column(name = "maxSalary")// k$ per year
    private int maxSalary; // k$ per year



    public JobPosting(int id, Company company, Set<Employee> employees, Set<Skill> skills, String jobTitle, String jobDescription, ProjectLocation projectLocation, EmploymentType employmentType, String city, String state, String country, Timestamp datePosted, int minSalary, int maxSalary) {
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
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Timestamp getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Timestamp datePosted) {
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

    @Override
    public String toString() {
        return "JobPosting{" +
                "id=" + id +
                ", company=" + company +
                ", employees=" + employees +
                ", skills=" + skills +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", projectLocation=" + projectLocation +
                ", employmentType=" + employmentType +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", datePosted=" + datePosted +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                '}';
    }
}
