package com.example.employez.service;

import com.example.employez.dao.UserDAO.UserDAO;
import com.example.employez.dao.employeeDAO.EmployeeDAO;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.User;
import com.example.employez.domain.enumPackage.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IService {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public boolean existEmployee(String email) {
        User user = userDAO.getByMail(email);
        if (user == null) {
            return false;
        }
        int userId = user.getId();
        Employee employee = employeeDAO.getById(userId);
        if (employee != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean existCompany(String email) {
        User user = userDAO.getByMail(email);
        if (!existEmployee(email)) {
            return true;
        }
        return false;
    }

    @Override
    public Role getRole(int userId) {
        User user = userDAO.getById(userId);
        if (user != null) {
            if (existEmployee(user.getEmail())) {
                return Role.EMPLOYEE;
            }
            return Role.COMPANY;
        }
        return Role.NON_EXISTED;
    }


}
