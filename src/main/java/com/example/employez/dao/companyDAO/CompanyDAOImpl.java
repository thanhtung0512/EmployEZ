package com.example.employez.dao.companyDAO;

import com.example.employez.domain.entity_class.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public Company getById(int id) {
        Company company = new Company();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = ("SELECT c.name FROM Company c WHERE c.id =" + id);
        List<String> res = session.createNativeQuery(hql, String.class).list();

        try {
            company.setName(res.get(0));
            company.setId((long) id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.getTransaction().commit();
        session.close();
        return company;

    }
}
