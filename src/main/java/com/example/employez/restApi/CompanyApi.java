package com.example.employez.restApi;


import com.example.employez.dao.companyDAO.CompanyDAO;
import com.example.employez.domain.entity_class.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CompanyApi {
    @Autowired
    private CompanyDAO companyDAO;
    @GetMapping("/company/{id}")
    public Company byId(@PathVariable int id) {
        return companyDAO.getById(id);
    }

}
