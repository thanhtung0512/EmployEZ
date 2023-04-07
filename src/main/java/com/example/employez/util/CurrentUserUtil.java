package com.example.employez.util;

import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.User;
import com.example.employez.repository.CompanyRepository;
import com.example.employez.repository.EmployeeRepository;
import com.example.employez.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserUtil {

    @Autowired
    private  SessionFactory sessionFactory;
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private  EmployeeRepository employeeRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.toString());
        String mail = authentication.getName();
        User user = userRepository.getUserByEmail(mail);
        return user;
    }

    public Employee employee (int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sql = "SELECT e.id FROM employee e  WHERE e.user_id = :userId";
        Long employeeId = session.createNativeQuery(sql, Long.class)
                .setParameter("userId", userId).getSingleResult();
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        session.getTransaction().commit();
        session.close();
        return employee;
    }

    public Company company (int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sql = "SELECT c.id FROM company c  WHERE c.user_id = :userId";
        Long companyId = session.createNativeQuery(sql, Long.class)
                .setParameter("userId", userId).getSingleResult();
        Company company = companyRepository.findCompanyById(companyId);
        session.getTransaction().commit();
        session.close();
        return company;
    }
}
