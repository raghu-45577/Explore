package com.explore.companyms.company.service;

import com.explore.companyms.company.dto.ReviewMessage;
import com.explore.companyms.company.model.Company;

import java.util.List;

public interface CompanyService {

    List<Company> findAll();
    void createCompany(Company company);
    Company getCompanyById(Long id);
    boolean deleteCompanyById(Long id);
    boolean updateCompanyById(Long id, Company updatedCompany);
    void updateCompanyRating(ReviewMessage reviewMessage);
}
