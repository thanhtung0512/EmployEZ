package com.example.employez.dao.employeeDAO;

import com.example.employez.domain.entity_class.Employee;

public interface EmployeeDAO {
    void save(Employee employee);

    Employee getByMail(String email);

    Employee getByUserId(int userId);
}
