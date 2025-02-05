package org.example.service;

import org.example.entity.Company;
import java.util.List;

public interface CompanyService {
    Company getCompanyById(int id);
    void saveCompany(Company company);
    void updateCompany(Company company);
    void deleteCompanyById(int id);
    List<Company> getAllCompanies();
    double calculateTotalRevenue(int companyId);
    List<Company> getCompaniesSortedByRevenue();
}
