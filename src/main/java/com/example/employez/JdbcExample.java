package com.example.employez;

import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.domain.entity_class.Skill;
import com.example.employez.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JdbcExample {
    public static void main(String[] args) {
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3307/employez";

        // Database credentials
        final String USER = "root";
        final String PASS = "Ab20122002";

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            String hql = ("SELECT j.id,j.city,j.country,j.datePosted,j.employmentType,j.jobDescription,j.jobTitle,j.maxSalary,j.minSalary,j.projectLocation,j.state,j.company FROM JobPosting j  ");
            Object[][] result = session.createQuery(hql, Object[][].class)
                    .getResultList().toArray(new Object[0][]);
            ArrayList<JobPosting> jobPostings = new ArrayList<>();
            List<Skill> skills = session.createQuery("SELECT s FROM Skill s", Skill.class).getResultList();
            for (int i = 0; i < 635; i++) {
                String des = (String) result[i][5];
                int id = (Integer) result[i][0];

                Set<Skill> skillSet = new HashSet<>();

                for (Skill skill : skills) {

                    String skillName = skill.getName();
                    if (des.contains(skillName) || des.contains(skillName.toLowerCase())) {
                        skillSet.add(skill);
                        System.out.println(id + " " + skill.getId());
                        String sql = "INSERT INTO job_required_skill(fk_job, fk_skill) VALUES (?, ?)";
                        stmt = conn.prepareStatement(sql);
                        // Set the parameter values
                        stmt.setInt(1, id);
                        stmt.setInt(2, skill.getId());
                        int rowsAffected = stmt.executeUpdate();
                        System.out.println(rowsAffected + " rows affected");


                    }
                }

            }


            // Prepare the SQL statement
            // Execute the statement


        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {

            // Finally block to close resources
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
