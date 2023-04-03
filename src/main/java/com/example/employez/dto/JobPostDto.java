package com.example.employez.dto;

import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.enumPackage.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostDto {
    private Integer id;
    private EmploymentType employmentType;
    private String jobTitle;
    private Company company;
    private String country;
    private Integer minSalary;
    private Integer maxSalary;
}
