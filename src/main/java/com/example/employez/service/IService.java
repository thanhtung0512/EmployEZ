package com.example.employez.service;

import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.enumPackage.Role;

public interface IService {
    boolean existEmployee(String email);
    boolean existCompany(String email);

    Role getRole(int userId);
}
