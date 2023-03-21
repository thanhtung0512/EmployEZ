package com.example.employez.domain;

import com.example.employez.domain.address.Address;
import com.example.employez.domain.address.CompanyAddress;
import com.example.employez.domain.enumPackage.CompanyType;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Company extends User {
    private String name;
    private CompanyType companyType;

    @OneToMany(mappedBy = "company")
    private Set<CompanyAddress> addressList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    public Company(int id, String passwordHash, String email, String name, CompanyType companyType, Set<CompanyAddress> addressList) {
        super(id, passwordHash, email);
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


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
