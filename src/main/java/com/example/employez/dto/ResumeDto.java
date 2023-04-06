package com.example.employez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Set;

@Data // for immutable object
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto {

    // General information

    private String firstName;
    private String middleName;
    private String surName;
    private String dateOfBirth;
    private String sex;
    private String jobTitle;

    // Contact information
    private String city;
    private String state;
    private String country;
    private String phone;
    private String email;
    private String website;

    // Education details
    private String graduation;
    private String university;
    private String degree;

    // Work experience
    private String companyName;
    private String jobPosition;
    private String location;
    private String dateFrom;
    private String dateTo;
    private String detailContribution;

    // Skill
    private String skill;

    private Set<String> skillSet;


}
