package com.example.employez.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "resume")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String firstName;

    private String lastName;



    private String education;

    private int yearOfExperience;

    private String email;
    private String phoneNumber;
    private String githubURL;


    @ManyToOne
    private Employee employee;

}
