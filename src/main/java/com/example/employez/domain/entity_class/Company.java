package com.example.employez.domain.entity_class;

import com.example.employez.domain.enumPackage.CompanyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "company")
public class Company {


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "name")
    private String name;

    @Column(name = "companyType")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType = CompanyType.PRODUCT;


    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<JobPosting> jobPostings;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
