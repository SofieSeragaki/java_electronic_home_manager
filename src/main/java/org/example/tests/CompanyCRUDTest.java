package org.example.tests;

import org.example.entity.Company;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.service.implService.CompanyServiceImpl;

import java.util.List;

public class CompanyCRUDTest {

    private CompanyServiceImpl companyService;

    public CompanyCRUDTest() {
        TestDataSetup.insertTestData();
        companyService = new CompanyServiceImpl();
    }

    public void testCreateCompany() {
        Company newCompany = new Company();
        newCompany.setName("New Company");
        newCompany.setAddress("New Street");
        newCompany.setRevenue(2000.0);

        try {
            companyService.saveCompany(newCompany);
            Company savedCompany = companyService.getCompanyById(newCompany.getId());

            if (savedCompany == null || !savedCompany.getName().equals("New Company")) {
                System.err.println("Test failed: Company creation failed.");
            } else {
                System.out.println("Test passed: Company created successfully.");
            }
        } catch (ValidationException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testReadCompany() {
        List<Company> companies = companyService.getAllCompanies();
        if (companies == null || companies.isEmpty()) {
            System.err.println("Test failed: No companies found.");
        } else {
            Company firstCompany = companies.get(0);
            if (firstCompany != null && "Poor Company".equals(firstCompany.getName())) {
                System.out.println("Test passed: Company read successfully.");
            } else {
                System.err.println("Test failed: Company data mismatch.");
            }
        }
    }

    public void testUpdateCompany() {
        try {
            Company company = companyService.getAllCompanies().get(0);
            company.setName("Updated Company");
            company.setRevenue(7000.0);

            companyService.updateCompany(company);

            Company updatedCompany = companyService.getCompanyById(company.getId());
            if (updatedCompany != null && "Updated Company".equals(updatedCompany.getName())
                    && updatedCompany.getRevenue() == 7000.0) {
                System.out.println("Test passed: Company updated successfully.");
            } else {
                System.err.println("Test failed: Company update failed.");
            }
        } catch (EntityNotFoundException | ValidationException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testDeleteCompany() {
        try {
            Company company = companyService.getAllCompanies().get(0);
            companyService.deleteCompanyById(company.getId());

            try {
                companyService.getCompanyById(company.getId());
                System.err.println("Test failed: Company was not deleted.");
            } catch (EntityNotFoundException e) {
                System.out.println("Test passed: Company deleted successfully.");
            }
        } catch (EntityNotFoundException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CompanyCRUDTest test = new CompanyCRUDTest();
        test.testCreateCompany();
        test.testReadCompany();
        test.testUpdateCompany();
        test.testDeleteCompany();
    }
}
