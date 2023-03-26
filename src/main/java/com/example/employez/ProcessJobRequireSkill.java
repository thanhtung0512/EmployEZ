package com.example.employez;

import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.JobPosting;

import com.example.employez.domain.entity_class.Skill;
import com.example.employez.domain.enumPackage.EmploymentType;
import com.example.employez.domain.enumPackage.ProjectLocation;
import com.example.employez.util.HibernateUtil;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ProcessJobRequireSkill {



    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();






    }
}
