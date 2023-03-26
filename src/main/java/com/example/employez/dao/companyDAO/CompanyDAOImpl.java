package com.example.employez.dao.companyDAO;

import com.example.employez.domain.entity_class.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Company company) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Company findById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Company company = session.get(Company.class,id);
        session.getTransaction().commit();
        session.close();
        return company;

    }
}
