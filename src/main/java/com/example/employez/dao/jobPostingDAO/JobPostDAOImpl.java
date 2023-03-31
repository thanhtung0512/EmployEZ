package com.example.employez.dao.jobPostingDAO;

import com.example.employez.dao.companyDAO.CompanyDAO;
import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.domain.entity_class.Skill;
import com.example.employez.domain.enumPackage.EmploymentType;
import com.example.employez.domain.enumPackage.ProjectLocation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
            jobPosting.setDatePosted((Date) result[i][3]);
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
            jobPosting.setDatePosted((Date) result[i][3]);
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
        hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state FROM JobPosting j " +
                "WHERE j.city LIKE :location AND j.jobTitle LIKE :jobTitle ");

        Object[][] result = session.createQuery(hql, Object[][].class)
                .setParameter("jobTitle", "%" + jobTitle + "%")
                .setParameter("location", "%" + location + "%")
                .getResultList().toArray(new Object[0][]);
        ArrayList<JobPosting> jobPostings = new ArrayList<>();
        for (int i = 0; i < result.length ; i++) {
            JobPosting jobPosting = new JobPosting();
            int id  = (int) result[i][0];
            jobPosting.setId(id);
            jobPosting.setCity((String) result[i][1]);
            jobPosting.setCountry((String) result[i][2]);
            jobPosting.setDatePosted((Date) result[i][3]);
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

            Long companyId = session.createNativeQuery("SELECT j.company_id FROM jobposting j WHERE j.id = " + id, Long.class)
                    .getSingleResult();
            System.out.println(companyId);

            jobPosting.setCompany(companyDAO.getById(Math.toIntExact(companyId)));

            jobPostings.add(jobPosting);
        }
        for (JobPosting jobPosting : jobPostings) {
            System.out.println(jobPosting.toString());
        }
        return jobPostings;

    }

    @Override
    public JobPosting getById(int id) {
        Session session = sessionFactory.openSession();
        String hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state FROM JobPosting j " +
                "WHERE j.id = :id");
        Object[][] result = session.createQuery(hql, Object[][].class)
                .setParameter("id", id)
                .getResultList().toArray(new Object[0][]);

        JobPosting jobPosting = new JobPosting();
        try {
            if (result.length > 0) {
                jobPosting.setId((Integer) result[0][0]);
                jobPosting.setCity((String) result[0][1]);
                jobPosting.setCountry((String) result[0][2]);
                jobPosting.setDatePosted((Date) result[0][3]);
                jobPosting.setEmploymentType((EmploymentType) result[0][4]);
                jobPosting.setJobDescription((String) result[0][5]);
                jobPosting.setJobTitle((String) result[0][6]);
                if (result[0][7] != null) {
                    jobPosting.setMaxSalary((int) result[0][7]);
                } else {
                    jobPosting.setMaxSalary(0);
                }
                if (result[0][8] != null) {
                    jobPosting.setMinSalary((Integer) result[0][8]);
                } else {
                    jobPosting.setMinSalary(0);
                }
                jobPosting.setProjectLocation((ProjectLocation) result[0][9]);
                jobPosting.setState((String) result[0][10]);

                int jobId = (int) result[0][0]; // Replace with the actual job ID
                Long companyId = session.createNativeQuery("SELECT j.company_id FROM jobposting j WHERE j.id = :id " , Long.class)
                        .setParameter("id",jobId)
                        .getSingleResult();
                jobPosting.setCompany(companyDAO.getById(Math.toIntExact(companyId)));
                String hqll = "SELECT s FROM Skill s INNER JOIN s.jobPostings j WHERE j.id = :jobId";
                ArrayList<Skill> skillSet
                        = (ArrayList<Skill>) session.createQuery(hqll, Skill.class).setParameter("jobId", jobId).getResultList();
                jobPosting.setSkills(skillSet);
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return jobPosting;
    }
}
