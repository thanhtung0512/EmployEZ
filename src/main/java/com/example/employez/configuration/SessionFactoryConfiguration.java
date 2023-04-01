package com.example.employez.configuration;


import com.example.employez.util.HibernateUtil;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SessionFactoryConfiguration {

    @Bean
    public SessionFactory getSessionFactory() {
        return HibernateUtil.getSessionFactory();
    }




}
