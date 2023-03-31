package com.example.employez.dao.UserDAO;

import com.example.employez.domain.entity_class.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getByMail(String email) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "SELECT u FROM User u WHERE u.email = :email";
        return session.createQuery(hql,User.class).setParameter("email",email).getSingleResult();
    }
}
