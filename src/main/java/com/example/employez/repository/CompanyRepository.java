package com.example.employez.repository;

import com.example.employez.domain.entity_class.Company;
import com.example.employez.domain.entity_class.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    Company findCompanyById(Long id);
    Company findByUser(User user);

    Company findCompanyByName(String name);
}
