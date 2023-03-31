package com.example.employez;

import com.example.employez.dao.employeeDAO.EmployeeDAO;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestSaveEmployee {
    @Autowired
    private static EmployeeDAO employeeDAO;

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Employee employee = new Employee();
        employee.setLastName("Thanh");
        employee.setFirstName("Tung");
        session.save(employee);
        session.getTransaction().commit();
        session.close();
    }
}
