package com.example.employez;

import com.example.employez.util.HibernateUtil;
import org.hibernate.Session;


public class ProcessJobRequireSkill {


    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();


    }
}
