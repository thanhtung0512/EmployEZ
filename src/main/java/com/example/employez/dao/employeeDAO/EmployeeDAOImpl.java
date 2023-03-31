package com.example.employez.dao.employeeDAO;

import com.example.employez.domain.entity_class.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public EmployeeDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void save(Employee employee) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Employee getByMail(String email) {
        /*Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = session.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                .setParameter("email", email).uniqueResult();
        System.out.println(employee.getEmail());
        session.getTransaction().commit();
        session.close();
        *//*return employee;*/
        return new Employee();
    }

    @Override
    public Employee getById(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "SELECT e FROM Employee e WHERE e.user.id = :userId";
        Employee employee = session.createQuery(hql, Employee.class)
                .setParameter("userId",userId).getSingleResult();
        if (employee == null) return null;
        return employee;
    }
}
