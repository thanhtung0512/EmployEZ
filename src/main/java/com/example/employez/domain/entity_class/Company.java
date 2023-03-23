package com.example.employez.domain.entity_class;

import com.example.employez.domain.address.CompanyAddress;
import com.example.employez.domain.enumPackage.CompanyType;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "company")
public class Company extends User {

    @Column(name = "name")
    private String name;

    @Column(name = "companyType")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @OneToMany(mappedBy = "company")
    private Set<CompanyAddress> addressList;


    @OneToMany(mappedBy = "company")
    private Set<JobPosting> jobPostings;



    public Company(String passwordHash, String email, String name, CompanyType companyType, Set<CompanyAddress> addressList) {
        super(passwordHash, email);
        this.name = name;
        this.companyType = companyType;
        this.addressList = addressList;

    }

    public Company() {
        super();
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

    public Set<CompanyAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(Set<CompanyAddress> addressList) {
        this.addressList = addressList;
    }
}
