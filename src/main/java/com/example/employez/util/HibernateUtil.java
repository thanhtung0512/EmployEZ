package com.example.employez.util;

import com.example.employez.domain.address.CompanyAddress;
import com.example.employez.domain.entity_class.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {


    private static String currentDB = "employez";
    private static String port = "3307";
    private static String pass = "Ab20122002";
    private static String user = "root";


    private static SessionFactory sessionFactory;

    public static Session getSession(String concreteDatabase, Class<?>... annotatedClass) {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3307/" + concreteDatabase)
                    .setProperty("hibernate.connection.username", "hbstudent")
                    .setProperty("hibernate.connection.password", "hbstudent")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect"); // add your entity classes here
            for (int i = 0; i < annotatedClass.length; i++) {
                configuration.addAnnotatedClass(annotatedClass[i]);
            }
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

    public static Session getSession(String concreteDatabase, String port, String user, String password, Class<?>... annotatedClass) {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:" + port + "/" + concreteDatabase)
                    .setProperty("hibernate.connection.username", user)
                    .setProperty("hibernate.connection.password", password)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                    .setProperty("hibernate.show_sql", "true"); // add your entity classes here
            for (int i = 0; i < annotatedClass.length; i++) {
                configuration.addAnnotatedClass(annotatedClass[i]);
            }
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

    public static SessionFactory getSessionFactory(String concreteDatabase, String port, String user, String password, Class<?>... annotatedClass) {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:" + port + "/" + concreteDatabase)
                    .setProperty("hibernate.connection.username", user)
                    .setProperty("hibernate.connection.password", password)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                    .setProperty("hibernate.show_sql", "true")
                    /*.setProperty("hibernate.hbm2ddl.auto", "create")*/; // add your entity classes here
            for (int i = 0; i < annotatedClass.length; i++) {
                configuration.addAnnotatedClass(annotatedClass[i]);
            }
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }


    public static SessionFactory getSessionFactory() {
        return getSessionFactory(currentDB, port, user, pass
                , Company.class
                , Employee.class
                , JobPosting.class
                , Resume.class
                , Skill.class
                , CompanyAddress.class
                , Course.class
                , User.class
                , Role.class
        );
    }

}







