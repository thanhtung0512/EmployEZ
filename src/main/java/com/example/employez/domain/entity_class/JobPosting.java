package com.example.employez.domain.entity_class;

import com.example.employez.domain.enumPackage.EmploymentType;
import com.example.employez.domain.enumPackage.ProjectLocation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "jobposting")
public class JobPosting {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @ManyToOne(
            cascade = {CascadeType.MERGE
                    , CascadeType.DETACH
                    , CascadeType.PERSIST
                    , CascadeType.REFRESH})
    private Company company = new Company();

    /*@ManyToMany(mappedBy = "jobPostings")
    private Set<Employee> employees = new HashSet<>();*/

    @ManyToMany(fetch = FetchType.LAZY
            , cascade = {CascadeType.MERGE
            , CascadeType.PERSIST
            , CascadeType.REFRESH
            , CascadeType.REFRESH})
    @JoinTable(name = "job_required_skill",
            joinColumns = {@JoinColumn(name = "fk_job")},
            inverseJoinColumns = {@JoinColumn(name = "fk_skill")})
    private Set<Skill> skills;

    @Column(name = "jobTitle")
    private String jobTitle;

    @Column(name = "jobDescription", columnDefinition = "TEXT")
    private String jobDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "projectLocation")
    private ProjectLocation projectLocation;


    @Enumerated(EnumType.STRING)
    @Column(name = "employmentType")
    private EmploymentType employmentType;


    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;


    @Column(name = "country")
    private String country;

    @Column(name = "datePosted")
    private Date datePosted;

    @Column(name = "minSalary")
    private Integer minSalary; //

    @Column(name = "maxSalary")// k$ per year
    private Integer maxSalary; // k$ per year




}
