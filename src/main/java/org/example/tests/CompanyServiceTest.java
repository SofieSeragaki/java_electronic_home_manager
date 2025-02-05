package org.example.tests;

import org.example.entity.Company;
import org.example.service.implService.CompanyServiceImpl;

import java.util.List;

public class CompanyServiceTest {

    private final CompanyServiceImpl companyService;

    public CompanyServiceTest() {
        TestDataSetup.insertTestData();
        this.companyService = new CompanyServiceImpl();
    }

    public void testGetCompaniesSortedByRevenue() {
        System.out.println("Testing getCompaniesSortedByRevenue...");

        try {
            List<Company> sortedCompanies = companyService.getCompaniesSortedByRevenue();

            if (sortedCompanies == null || sortedCompanies.isEmpty()) {
                System.err.println("Test failed: No companies found.");
                return;
            }

            // test
            boolean isSorted = true;
            for (int i = 0; i < sortedCompanies.size() - 1; i++) {
                double currentRevenue = companyService.calculateTotalRevenue(sortedCompanies.get(i).getId());
                double nextRevenue = companyService.calculateTotalRevenue(sortedCompanies.get(i + 1).getId());
                if (currentRevenue < nextRevenue) {
                    isSorted = false;
                    break;
                }
            }

            if (isSorted) {
                System.out.println("Test passed: Companies are sorted by revenue in descending order.");
            } else {
                System.err.println("Test failed: Companies are not sorted correctly.");
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CompanyServiceTest test = new CompanyServiceTest();
        test.testGetCompaniesSortedByRevenue();
    }
}
