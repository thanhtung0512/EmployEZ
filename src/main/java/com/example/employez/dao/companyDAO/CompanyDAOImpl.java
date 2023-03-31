package com.example.employez.dao.companyDAO;

import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.enumPackage.CompanyType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        String hql = ("SELECT c.name FROM Company c WHERE c.id = :id");
        String res = session.createNativeQuery(hql, String.class)
                .setParameter("id", id)
                .getSingleResult();


        try {
            company.setName(res);
            company.setId((long) id);
            // company.setCompanyType((CompanyType) res[0][1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.getTransaction().commit();
        session.close();
        return company;

    }
}
