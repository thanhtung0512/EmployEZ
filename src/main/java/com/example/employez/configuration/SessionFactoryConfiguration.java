package com.example.employez.configuration;


import com.example.employez.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionFactoryConfiguration {

    @Bean(name = "entityManagerFactory")
    public SessionFactory getSessionFactory() {
        return HibernateUtil.getSessionFactory();
    }




}
