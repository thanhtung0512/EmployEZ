package com.example.employez.domain.address;

import com.example.employez.domain.Company;
import jakarta.persistence.*;

@Entity
public class CompanyAddress {

    @ManyToOne
    private Company company;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private String city;
    private String state;
    private int zipcode;


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



    public CompanyAddress() {
        super();

    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
