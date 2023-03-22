package com.example.employez.restApi;


import co.elastic.clients.elasticsearch.ml.Job;
import com.example.employez.domain.JobPosting;
import com.example.employez.domain.enumPackage.EmploymentType;
import com.example.employez.domain.enumPackage.ProjectLocation;
import com.example.employez.util.HibernateUtil;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class JobPostingApi {

    @GetMapping("/jobposts/{numbers}")
    @Transactional
    public List<JobPosting> jobPostingList(@PathVariable int numbers) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state FROM JobPosting j");
        Object[][] result = session.createQuery(hql, Object[][].class)
                .getResultList().toArray(new Object[0][]);

//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j <= result[i].length; j++) {
//                System.out.print(result[i][j] + " ");
//            }
//        }

        ArrayList<JobPosting> jobPostings = new ArrayList<>(numbers);

        for (int i = 0; i < numbers; i++) {
            JobPosting jobPosting = new JobPosting();
            jobPosting.setId((Integer) result[i][0]);
            jobPosting.setCity((String) result[i][1]);
            jobPosting.setCountry((String) result[i][2]);
            jobPosting.setDatePosted((Timestamp) result[i][3]);
            jobPosting.setEmploymentType((EmploymentType) result[i][4]);
            jobPosting.setJobDescription((String) result[i][5]);
            jobPosting.setJobTitle((String) result[i][6]);
            jobPosting.setMaxSalary((Integer) result[i][7]);
            jobPosting.setMinSalary((Integer) result[i][8]);
            jobPosting.setProjectLocation((ProjectLocation) result[i][9]);
            jobPosting.setState((String) result[i][10]);
            jobPostings.add(jobPosting);
        }
        return jobPostings;
    }

}
