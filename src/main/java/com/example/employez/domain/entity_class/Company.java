package com.example.employez.domain.entity_class;

import com.example.employez.domain.enumPackage.CompanyType;
import io.netty.util.collection.CharObjectMap;
import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;

import java.util.Set;

@Entity
@Table(name = "company")
public class Company  {



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "name")
    private String name;

    @Column(name = "companyType")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType = CompanyType.PRODUCT;


    @OneToMany(mappedBy = "company")
    private Set<JobPosting> jobPostings;
    @Id
    @Column(name = "id")
    private Long id;


    public Company() {
        companyType = CompanyType.PRODUCT;
        name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType =(companyType);
    }

    public Company(User user, String name, CompanyType companyType, Set<JobPosting> jobPostings, Long id) {
        setUser(user);
        setName(name);
        setCompanyType(companyType);
        setJobPostings(jobPostings);
        setId(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<JobPosting> getJobPostings() {
        return jobPostings;
    }

    public void setJobPostings(Set<JobPosting> jobPostings) {
        this.jobPostings = jobPostings;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


}
