package com.example.employez.dao.employeeDAO;

import com.example.employez.domain.entity_class.Employee;
import com.example.employez.dto.EmployeeDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        return null;
    }


    @Override
    public Employee getByUserId(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "SELECT e FROM Employee e WHERE e.user.id = :userId";
        Employee employee = session.createQuery(hql, Employee.class)
                .setParameter("userId", userId).getSingleResult();
        if (employee == null) return null;
        return employee;
    }

    public EmployeeDto getEmployee(int employeeId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String email = (String) session.createNativeQuery("SELECT email from employee e join user u on u.id = e.user_id")
                .getSingleResult();
        String selectEmpFields = "SELECT e.id, e.firstName, e.lastName, e.country, e.jobTitle, e.university, e.phone,e.city,e.country " +
                "FROM Employee e " +
                "WHERE e.id = :empId";
        Object[][] result = session.createQuery(selectEmpFields, Object[][].class)
                .setParameter("empId", employeeId).getResultList().toArray(new Object[0][]);
        EmployeeDto employeeDto = new EmployeeDto();
        for (Object[] object : result) {
            employeeDto.setId((Long) object[0]);
            employeeDto.setFirstName((String) object[1]);
            employeeDto.setLastName((String) object[2]);
            employeeDto.setCountry((String) object[3]);
            employeeDto.setJobTitle((String) object[4]);
            employeeDto.setUniversity((String) object[5]);
            employeeDto.setPhone((String) object[6]);
            employeeDto.setCity((String) object[7]);
            employeeDto.setCountry((String) object[8]);
            employeeDto.setEmail(email);
        }
        session.getTransaction().commit();
        session.close();
        return employeeDto;
    }
}
