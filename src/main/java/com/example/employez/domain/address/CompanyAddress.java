package com.example.employez.domain.address;

import com.example.employez.domain.Company;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CompanyAddress extends Address{

    @ManyToOne
    private Company company;
    private Long id;

    public CompanyAddress(String city, String state, int zipcode) {
        super(city, state, zipcode);
    }

    public CompanyAddress() {
        super();

    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
