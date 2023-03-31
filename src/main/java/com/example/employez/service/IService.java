package com.example.employez.service;

import com.example.employez.domain.entity_class.Employee;

public interface IService {
    boolean existEmployee(String email);
    boolean existCompany(String email);
}
