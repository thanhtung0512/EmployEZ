package com.example.employez.util;

import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.net.ServerSocket;

public class TestDB {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User employee =  session.createQuery("SELECT e FROM Employee e WHERE e.email = :email",Employee.class)
                .setParameter("email", "cudamset05@gmail.com").uniqueResult();
        System.out.println(employee.getEmail());
    }
}
