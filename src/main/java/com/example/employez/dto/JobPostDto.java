package com.example.employez.dto;

import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.enumPackage.ApplyingJobState;
import com.example.employez.domain.enumPackage.EmploymentType;
import com.example.employez.domain.enumPackage.ProjectLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JobPostDto {

    private Integer id;
    private EmploymentType employmentType;
    private String jobTitle;
    private Company company;
    private String country;
    private String city;
    private Integer minSalary;
    private Integer maxSalary;
    private Long daySincePosted;
    private Date datePosted;
    private String jobDescription;
    private ProjectLocation projectLocation;
    private ApplyingJobState applyingJobState;
    private String state;
}
