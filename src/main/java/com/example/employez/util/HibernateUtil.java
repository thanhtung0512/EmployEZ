package com.example.employez.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

   /* private static String currentDB = "web_customer_tracker";
    private static String port = "3307";
    private static String user = "springstudent";
    private static String pass = "springstudent";*/



    private static SessionFactory sessionFactory;

    public static Session getSession(String concreteDatabase, Class<?>... annotatedClass) {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3307/" + concreteDatabase)
                    .setProperty("hibernate.connection.username", "hbstudent")
                    .setProperty("hibernate.connection.password", "hbstudent")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                    .setProperty("hibernate.show_sql", "true"); // add your entity classes here
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

    /*public static Session getCurrentSession() {
        return getSession(currentDB,port,user,pass, Customer.class);
    }

    public static SessionFactory getSessionFactory() {
        getSession(currentDB,port,user,pass, customerClass);
        return sessionFactory;
    }*/

    public static SessionFactory getSessionFactory() {

        return null;
    }

}







