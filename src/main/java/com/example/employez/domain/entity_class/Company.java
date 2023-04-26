package com.example.employez.domain.entity_class;

import com.example.employez.domain.enumPackage.CompanyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "company")
public class Company {


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "companyType")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType = CompanyType.PRODUCT;


    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<JobPosting> jobPostings;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;


    public Company() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        this.companyType = companyType;
    }

    public Set<JobPosting> getJobPostings() {
        return jobPostings;
    }

    public void setJobPostings(Set<JobPosting> jobPostings) {
        this.jobPostings = jobPostings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company(User user, String name, CompanyType companyType, Set<JobPosting> jobPostings, Long id) {
        this.user = user;
        this.name = name;
        this.companyType = companyType;
        this.jobPostings = jobPostings;
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
