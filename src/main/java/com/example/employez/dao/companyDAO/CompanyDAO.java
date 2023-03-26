package com.example.employez.dao.companyDAO;

import com.example.employez.domain.entity_class.Company;

public interface CompanyDAO {

    void save(Company company);

    Company findById(int id);
}
