package com.example.employez;

import com.example.employez.dao.employeeDAO.EmployeeDAO;
import com.example.employez.domain.entity_class.Role;
import com.example.employez.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestSaveEmployee {


    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Role role = session.get(Role.class, 1);
        System.out.println(role
                .getName());
        session.getTransaction().commit();
        session.close();
    }
}
