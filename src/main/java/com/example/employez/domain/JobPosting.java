package com.example.employez.domain;

import com.example.employez.domain.address.Address;
import com.example.employez.domain.address.CompanyAddress;
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
    private Address location;
    private Date datePosted;
    private int minSalary;
    private int maxSalary;


}
