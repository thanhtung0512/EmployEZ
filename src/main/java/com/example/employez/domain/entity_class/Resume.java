package com.example.employez.domain.entity_class;

import javax.persistence.*;

@Entity
@Table(name = "resume")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "education")
    private String education;


    @Column(name = "yearOfExperience")
    private int yearOfExperience;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "githubURL")
    private String githubURL;


    @ManyToOne
    private Employee employee;

}
