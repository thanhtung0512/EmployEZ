package com.example.employez.dao.jobPostingDAO;

import com.example.employez.dao.companyDAO.CompanyDAO;
import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.domain.entity_class.JobSkill;
import com.example.employez.domain.entity_class.Skill;
import com.example.employez.domain.enumPackage.EmploymentType;
import com.example.employez.domain.enumPackage.ProjectLocation;
import com.example.employez.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class JobPostDAOImpl implements JobPostDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CompanyDAO companyDAO;

    @Override
    @Transactional
    public JobPosting createJobPosting() {
        return new JobPosting();
    }

    @Override
    @Transactional
    public void deleteJobPosting(int id) {

    }

    @Override
    @Transactional
    public List<JobPosting> getJobPostingByLocation(String... location) {
        return null;
    }

    @Override
    @Transactional
    public List<JobPosting> getJobPostingByNewestDate() {
        return null;
    }

    @Override
    @Transactional
    public List<JobPosting> getJobPostingByJobTitle(String jobTitle) {
        return null;
    }

    @Override
    public List<JobPosting> jobPostingList(int numbers) {
        Session session = sessionFactory.openSession();
        String hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state,j.company FROM JobPosting j  ");
        Object[][] result = session.createQuery(hql, Object[][].class)
                .getResultList().toArray(new Object[0][]);
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
            if (result[i][7] != null) {
                jobPosting.setMaxSalary((int) result[i][7]);
            }
            if (result[i][8] != null) {
                jobPosting.setMinSalary((Integer) result[i][8]);
            }
            jobPosting.setProjectLocation((ProjectLocation) result[i][9]);
            jobPosting.setState((String) result[i][10]);
            jobPosting.setCompany((Company) result[i][11]);
            jobPostings.add(jobPosting);
        }
        return jobPostings;
    }

    @Override
    public List<JobPosting> jobPostingListByNameAreaField(String name, String area, String field) {
        Session session = sessionFactory.openSession();
        String hql = "";
        if (area.equals("Usa")) {
            hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state FROM JobPosting j " +
                    "WHERE j.country = :area OR j.jobTitle LIKE :name " +
                    "OR j.jobTitle LIKE :field ");
        } else {
            hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state FROM JobPosting j " +
                    "WHERE j.state = :area OR j.jobTitle LIKE :name " +
                    "OR j.jobTitle LIKE :field ");
        }

        Object[][] result = session.createQuery(hql, Object[][].class)
                .setParameter("name", "%" + name + "%")
                .setParameter("field", "%" + field + "%")
                .setParameter("area", area).setMaxResults(10)
                .getResultList().toArray(new Object[0][]);

        ArrayList<JobPosting> jobPostings = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            JobPosting jobPosting = new JobPosting();
            jobPosting.setId((Integer) result[i][0]);
            jobPosting.setCity((String) result[i][1]);
            jobPosting.setCountry((String) result[i][2]);
            jobPosting.setDatePosted((Timestamp) result[i][3]);
            jobPosting.setEmploymentType((EmploymentType) result[i][4]);
            jobPosting.setJobDescription((String) result[i][5]);
            jobPosting.setJobTitle((String) result[i][6]);
            if (result[i][7] != null) {
                jobPosting.setMaxSalary((int) result[i][7]);
            }
            if (result[i][8] != null) {
                jobPosting.setMinSalary((Integer) result[i][8]);
            }
            jobPosting.setProjectLocation((ProjectLocation) result[i][9]);
            jobPosting.setState((String) result[i][10]);
            jobPostings.add(jobPosting);
        }



        for (JobPosting jobPosting : jobPostings) {
            System.out.println(jobPosting.toString());
        }

        return jobPostings;
    }

    @Override
    public List<JobPosting> jobPostingListByTwoFields(String jobTitle, String location) {
        Session session = sessionFactory.openSession();
        String hql = "";
            hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state,j.company FROM JobPosting j " +
                    "WHERE j.city LIKE :location AND j.jobTitle LIKE :jobTitle ");

        Object[][] result = session.createQuery(hql, Object[][].class)
                .setParameter("jobTitle", "%" + jobTitle + "%")
                .setParameter("location", "%" + location + "%")
                .getResultList().toArray(new Object[0][]);
        ArrayList<JobPosting> jobPostings = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            JobPosting jobPosting = new JobPosting();
            jobPosting.setId((Integer) result[i][0]);
            jobPosting.setCity((String) result[i][1]);
            jobPosting.setCountry((String) result[i][2]);
            jobPosting.setDatePosted((Timestamp) result[i][3]);
            jobPosting.setEmploymentType((EmploymentType) result[i][4]);
            jobPosting.setJobDescription((String) result[i][5]);
            jobPosting.setJobTitle((String) result[i][6]);
            if (result[i][7] != null) {
                jobPosting.setMaxSalary((int) result[i][7]);
            }
            if (result[i][8] != null) {
                jobPosting.setMinSalary((Integer) result[i][8]);
            }
            jobPosting.setProjectLocation((ProjectLocation) result[i][9]);
            jobPosting.setState((String) result[i][10]);
            jobPosting.setCompany((Company) result[i][11]);
            jobPostings.add(jobPosting);
        }
        for (JobPosting jobPosting : jobPostings) {
            System.out.println(jobPosting.toString());
        }


        return jobPostings;

    }

    @Override
    public JobPosting jobPostingById(int id) {
        Session session = sessionFactory.openSession();
        String hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state,j.company FROM JobPosting j " +
                "WHERE j.id = :id");
        Object[][] result = session.createQuery(hql, Object[][].class)
                .setParameter("id", id)
                .getResultList().toArray(new Object[0][]);

        JobPosting jobPosting = new JobPosting();
        try {
            jobPosting.setId((Integer) result[0][0]);
            jobPosting.setCity((String) result[0][1]);
            jobPosting.setCountry((String) result[0][2]);
            jobPosting.setDatePosted((Timestamp) result[0][3]);
            jobPosting.setEmploymentType((EmploymentType) result[0][4]);
            jobPosting.setJobDescription((String) result[0][5]);
            jobPosting.setJobTitle((String) result[0][6]);
            if (result[0][7] != null) {
                jobPosting.setMaxSalary((int) result[0][7]);
            }
            if (result[0][8] != null) {
                jobPosting.setMinSalary((Integer) result[0][8]);
            }
            jobPosting.setProjectLocation((ProjectLocation) result[0][9]);
            jobPosting.setState((String) result[0][10]);
            jobPosting.setCompany((Company) result[0][11]);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return jobPosting;
    }
}
