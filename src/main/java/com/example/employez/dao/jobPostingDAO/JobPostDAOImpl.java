package com.example.employez.dao.jobPostingDAO;

import com.example.employez.dao.companyDAO.CompanyDAO;
import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.domain.enumPackage.EmploymentType;
import com.example.employez.domain.enumPackage.ProjectLocation;
import com.example.employez.dto.JobPostDto;
import com.example.employez.util.DayUtil;
import com.example.employez.util.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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
    public List<JobPostDto> getJobPostingByNewestDate(boolean getFull, int num) {
        Session session = sessionFactory.openSession();
        String hql = "";
        hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state FROM JobPosting j " +
                "ORDER BY j.datePosted DESC");

        Object[][] result;
        if (getFull) {
            result = session.createQuery(hql, Object[][].class)
                    .getResultList().toArray(new Object[0][]);
        }
        else {
            result = session.createQuery(hql, Object[][].class).setMaxResults(num)
                    .getResultList().toArray(new Object[0][]);
        }

        List<JobPostDto> jobPostings = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            JobPostDto jobPostDto = new JobPostDto();
            Date posted = (Date) result[i][3];
            int id = (int) result[i][0];
            jobPostDto.setId(id);
            jobPostDto.setCity((String) result[i][1]);
            jobPostDto.setCountry((String) result[i][2]);
            jobPostDto.setDatePosted((Date) result[i][3]);

            jobPostDto.setDaySincePosted(
                    DayUtil.daysBetweenNowAndSpecificDate(posted));

            jobPostDto.setEmploymentType((EmploymentType) result[i][4]);

            jobPostDto.setJobDescription((String) result[i][5]);
            jobPostDto.setJobTitle((String) result[i][6]);
            if (result[i][7] != null) {
                jobPostDto.setMaxSalary((int) result[i][7]);
            }
            if (result[i][8] != null) {
                jobPostDto.setMinSalary((Integer) result[i][8]);
            }
            jobPostDto.setProjectLocation((ProjectLocation) result[i][9]);
            jobPostDto.setCity((String) result[i][10]);

            Long companyId = session.createNativeQuery("SELECT j.company_id FROM jobposting j WHERE j.id = " + id, Long.class)
                    .getSingleResult();
            System.out.println(companyId);

            jobPostDto.setCompany(companyDAO.getById(companyId));

            jobPostings.add(jobPostDto);
        }
        for (JobPostDto jobPostDto : jobPostings) {
            System.out.println(jobPostDto.toString());
        }

        session.close();
        return jobPostings;
    }

    @Override
    @Transactional
    public List<JobPosting> getJobPostingByJobTitle(String jobTitle) {
        return null;
    }

    @Override
    @Transactional
    public List<JobPosting> jobPostingList(int numbers) {
        Session session = sessionFactory.openSession();
        String hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state,j.company FROM JobPosting j  ORDER BY j.datePosted DESC ");
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
        session.getTransaction().commit();
        session.close();
        return jobPostings;
    }

    @Override
    @Transactional
    public List<JobPosting> jobPostingListByNameAreaField(String name, String area, String field) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "";
        if (area.equals("Usa")) {
            hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state FROM JobPosting j " +
                    "WHERE j.country = :area OR j.jobTitle LIKE :name " +
                    "OR j.jobTitle LIKE :field ORDER BY j.datePosted DESC");
        } else {
            hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state FROM JobPosting j " +
                    "WHERE j.state = :area OR j.jobTitle LIKE :name " +
                    "OR j.jobTitle LIKE :field ORDER BY j.datePosted DESC");
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
        session.getTransaction().commit();
        session.close();
        return jobPostings;
    }

    @Override
    @Transactional
    public List<JobPostDto> jobPostingListByTwoFields(String jobTitle, String location) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "";
        hql = ("SELECT j.id FROM JobPosting j " +
                "WHERE j.city LIKE :location AND j.jobTitle LIKE :jobTitle ");

        String hqll = "SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state FROM JobPosting j "
                + "WHERE j.jobTitle LIKE :jobTitle AND (j.city LIKE :location OR j.state LIKE :location) ORDER BY j.datePosted DESC";

        Object[][] result = session.createQuery(hqll, Object[][].class)
                .setParameter("jobTitle", "%" + jobTitle + "%")
                .setParameter("location", "%" + location + "%")
                .getResultList().toArray(new Object[0][]);


        List<JobPostDto> jobPostDtos = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            JobPostDto jobPostDto = new JobPostDto();
            Date posted = (Date) result[i][3];
            int id = (int) result[i][0];
            jobPostDto.setId(id);
            jobPostDto.setCity((String) result[i][1]);
            jobPostDto.setCountry((String) result[i][2]);
            jobPostDto.setDatePosted((Date) result[i][3]);

            jobPostDto.setDaySincePosted(
                    DayUtil.daysBetweenNowAndSpecificDate(posted));

            jobPostDto.setEmploymentType((EmploymentType) result[i][4]);

            jobPostDto.setJobDescription((String) result[i][5]);
            jobPostDto.setJobTitle((String) result[i][6]);
            if (result[i][7] != null) {
                jobPostDto.setMaxSalary((int) result[i][7]);
            }
            if (result[i][8] != null) {
                jobPostDto.setMinSalary((Integer) result[i][8]);
            }
            jobPostDto.setProjectLocation((ProjectLocation) result[i][9]);
            jobPostDto.setCity((String) result[i][10]);

            Long companyId = session.createNativeQuery("SELECT j.company_id FROM jobposting j WHERE j.id = " + id, Long.class)
                    .getSingleResult();
            System.out.println(companyId);

            jobPostDto.setCompany(companyDAO.getById(companyId));

            jobPostDtos.add(jobPostDto);
        }
        /*for (JobPosting jobPosting : jobPostings) {
            System.out.println(jobPosting.toString());
        }*/
        session.getTransaction().commit();
        session.close();
        return jobPostDtos;

    }

    @Override
    @Transactional
    @Cacheable(value = "jobPost")
    public Pair<JobPosting, Set<String>> getById(int id) {
        Session session = sessionFactory.openSession();
        String hql = ("SELECT j.id,j.city" +
                ",j.country,j.datePosted" +
                ",j.employmentType,j.jobDescription" +
                ",j.jobTitle,j.maxSalary" +
                ",j.minSalary,j.projectLocation" +
                ",j.state FROM JobPosting j " +
                "WHERE j.id = :id ORDER BY j.datePosted DESC");
        Object[][] result = session.createQuery(hql, Object[][].class)
                .setParameter("id", id)
                .getResultList().toArray(new Object[0][]);

        JobPosting jobPosting = new JobPosting();
        Set<String> skillName = new HashSet<>();


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
                    jobPosting.setMaxSalary((Integer) result[0][7]);
                }
                if (result[0][8] != null) {
                    jobPosting.setMinSalary((Integer) result[0][8]);
                }
                jobPosting.setProjectLocation((ProjectLocation) result[0][9]);
                jobPosting.setState((String) result[0][10]);

                int jobId = (int) result[0][0]; // Replace with the actual job ID
                Long companyId = session.createNativeQuery("SELECT j.company_id FROM jobposting j " +
                                "WHERE j.id = :id ", Long.class)
                        .setParameter("id", jobId)
                        .getSingleResult();
                jobPosting.setCompany(companyDAO.getById(companyId));// fixing this line

                String hqll = "SELECT s.name FROM skill s WHERE s.id IN " +
                        "(Select jrs.fk_skill " +
                        "FROM job_required_skill jrs " +
                        "WHERE jrs.fk_job = :jobId)";
                skillName = new HashSet<>(session.createNativeQuery(hqll, String.class).setParameter("jobId", jobId).list());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Pair<JobPosting, Set<String>> pair = new Pair<>(jobPosting, skillName);
        session.close();
        return pair;
    }


    @Override
    @Transactional
    public List<JobPostDto> getBySkill(int skillId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ArrayList<JobPostDto> jobPostings = new ArrayList<>();
        String query = "SELECT jrs.fk_job FROM job_required_skill jrs WHERE fk_skill = :skillId  ";
        List<Integer> jobPostId =  session.createNativeQuery(query)
                .setParameter("skillId", skillId).getResultList();
        for (int jobId : jobPostId) {
            jobPostings.add(getJobPostDtoById(jobId).getKey());
        }
        session.getTransaction().commit();
        session.close();
        return jobPostings;
    }

    @Override
    @Transactional
    public List<JobPostDto> getBySkill(String skillName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sql = "SELECT s.id FROM skill s WHERE s.name = :skillName";
        Integer skillId = session.createNativeQuery(sql, Integer.class)
                .setParameter("skillName", skillName).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return getBySkill(skillId);
    }


    public List<JobPostDto> getBySalaryRange() {
        int maxNumber = 5;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sql = " SELECT j.jobTitle,j.company,j.state,j.country,j.maxSalary as maxSal,j.minSalary as minSal " +
                "FROM JobPosting j " +
                "WHERE j.minSalary is not null and j.maxSalary is not null " +
                "ORDER BY j.maxSalary DESC,  j.datePosted DESC ";
        Object[][] result = session.createQuery(sql, Object[][].class)
                .getResultList().toArray(new Object[0][]);
        List<JobPostDto> jobPostDtos = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            JobPostDto jobPostDto = new JobPostDto();
            jobPostDto.setJobTitle((String) result[i][0]);
            jobPostDto.setCompany((Company) result[i][1]);
            jobPostDto.setCity((String) result[i][2]);
            jobPostDto.setCountry((String) result[i][3]);
            jobPostDto.setMaxSalary((Integer) result[i][4]);
            jobPostDto.setMinSalary((Integer) result[i][5]);
            jobPostDtos.add(jobPostDto);
        }
        session.getTransaction().commit();
        session.close();
        return jobPostDtos;
    }

    public List<JobPostDto> getByCompanyId(Long companyId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sql = " SELECT j.jobTitle,j.company,j.state,j.country,j.maxSalary " +
                "AS maxSal,j.minSalary AS minSal,j.datePosted,j.id, j.projectLocation " +
                "FROM JobPosting j " +
                "WHERE j.company.id = "
                + companyId;
        Object[][] result = session.createQuery(sql, Object[][].class)
                .getResultList().toArray(new Object[0][]);
        List<JobPostDto> jobPostDtos = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            JobPostDto jobPostDto = new JobPostDto();
            jobPostDto.setId((Integer) result[i][7]);
            jobPostDto.setJobTitle((String) result[i][0]);
            jobPostDto.setCompany((Company) result[i][1]);
            jobPostDto.setCity((String) result[i][2]);
            jobPostDto.setCountry((String) result[i][3]);
            jobPostDto.setMaxSalary((Integer) result[i][4]);
            jobPostDto.setMinSalary((Integer) result[i][5]);
            jobPostDto.setDaySincePosted(DayUtil.daysBetweenNowAndSpecificDate((Date) result[i][6]));
            jobPostDto.setProjectLocation((ProjectLocation) result[i][8]);
            jobPostDtos.add(jobPostDto);
        }
        session.getTransaction().commit();
        session.close();
        return jobPostDtos;
    }

    public JobPostDto getById(Long jobId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sql = " SELECT j.jobTitle,j.company,j.state,j.country,j.maxSalary " +
                "AS maxSal,j.minSalary AS minSal,j.datePosted,j.id, j.projectLocation, j.jobDescription,j.employmentType " +
                "FROM JobPosting j " +
                "WHERE j.id = "
                + jobId;
        Object[][] result = session.createQuery(sql, Object[][].class)
                .getResultList().toArray(new Object[0][]);
        JobPostDto jobPostDto = new JobPostDto();
        for (int i = 0; i < result.length; i++) {
            jobPostDto.setId((Integer) result[i][7]);
            jobPostDto.setJobTitle((String) result[i][0]);
            jobPostDto.setCompany((Company) result[i][1]);
            jobPostDto.setCity((String) result[i][2]);
            jobPostDto.setCountry((String) result[i][3]);
            jobPostDto.setMaxSalary((Integer) result[i][4]);
            jobPostDto.setMinSalary((Integer) result[i][5]);
            jobPostDto.setDaySincePosted(DayUtil.daysBetweenNowAndSpecificDate((Date) result[i][6]));
            jobPostDto.setProjectLocation((ProjectLocation) result[i][8]);
            jobPostDto.setJobDescription((String) result[i][9]);
            jobPostDto.setEmploymentType((EmploymentType) result[i][10]);

        }
        session.getTransaction().commit();
        session.close();
        return jobPostDto;
    }

    public Pair<JobPostDto, Set<String>> getJobPostDtoById(int id) {
        Session session = sessionFactory.openSession();
        String hql = ("SELECT j.id,j.city" +
                ",j.country,j.datePosted" +
                ",j.employmentType,j.jobDescription" +
                ",j.jobTitle,j.maxSalary" +
                ",j.minSalary,j.projectLocation" +
                ",j.state,j.datePosted FROM JobPosting j " +
                "WHERE j.id = :id ORDER BY j.datePosted DESC");
        Object[][] result = session.createQuery(hql, Object[][].class)
                .setParameter("id", id)
                .getResultList().toArray(new Object[0][]);

        JobPostDto jobPostDto = new JobPostDto();
        Set<String> skillName = new HashSet<>();


        try {
            if (result.length > 0) {
                jobPostDto.setId((Integer) result[0][0]);
                jobPostDto.setCity((String) result[0][1]);
                jobPostDto.setCountry((String) result[0][2]);
                jobPostDto.setDatePosted((Date) result[0][3]);
                jobPostDto.setEmploymentType((EmploymentType) result[0][4]);
                jobPostDto.setJobDescription((String) result[0][5]);
                jobPostDto.setJobTitle((String) result[0][6]);
                if (result[0][7] != null) {
                    jobPostDto.setMaxSalary((Integer) result[0][7]);
                }
                if (result[0][8] != null) {
                    jobPostDto.setMinSalary((Integer) result[0][8]);
                }
                jobPostDto.setProjectLocation((ProjectLocation) result[0][9]);
                jobPostDto.setState((String) result[0][10]);
                jobPostDto.setDaySincePosted(DayUtil.daysBetweenNowAndSpecificDate((Date) result[0][11]));

                int jobId = (int) result[0][0]; // Replace with the actual job ID
                Long companyId = session.createNativeQuery("SELECT j.company_id FROM jobposting j " +
                                "WHERE j.id = :id ", Long.class)
                        .setParameter("id", jobId)
                        .getSingleResult();
                jobPostDto.setCompany(companyDAO.getById(companyId));// fixing this line

                String hqll = "SELECT s.name FROM skill s WHERE s.id IN " +
                        "(Select jrs.fk_skill " +
                        "FROM job_required_skill jrs " +
                        "WHERE jrs.fk_job = :jobId)";
                skillName = new HashSet<>(session.createNativeQuery(hqll, String.class).setParameter("jobId", jobId).list());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Pair<JobPostDto, Set<String>> pair = new Pair<>(jobPostDto, skillName);
        session.close();
        return pair;
    }
}
