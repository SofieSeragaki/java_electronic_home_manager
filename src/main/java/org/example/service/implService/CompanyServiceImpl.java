package org.example.service.implService;

import org.example.dao.CompanyDAO;
import org.example.dao.PaymentDAO;
import org.example.dao.implDAO.CompanyDAOImpl;
import org.example.dao.implDAO.PaymentDAOImpl;
import org.example.entity.Company;
import org.example.entity.Payment;
import org.example.service.CompanyService;

import java.util.Comparator;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    private final CompanyDAO companyDAO;
    private final PaymentDAO paymentDAO;

    public CompanyServiceImpl() {
        this.companyDAO = new CompanyDAOImpl();
        this.paymentDAO = new PaymentDAOImpl();
    }

    @Override
    public Company getCompanyById(int id) {
        return companyDAO.findById(id);
    }

    @Override
    public void saveCompany(Company company) {
        validateCompany(company);
        companyDAO.save(company);
    }

    @Override
    public void updateCompany(Company company) {
        validateCompany(company);
        companyDAO.update(company);
    }

    @Override
    public void deleteCompanyById(int id) {
        companyDAO.deleteById(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyDAO.findAll();
    }

    @Override
    public double calculateTotalRevenue(int companyId) {
        Company company = getCompanyById(companyId);
        if (company == null) {
            throw new IllegalArgumentException("Company with ID " + companyId + " not found");
        }

        List<Payment> payments = paymentDAO.findAll();
        return payments.stream()
                .filter(payment -> payment.getCompany().getId() == companyId)
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    @Override
    public List<Company> getCompaniesSortedByRevenue() {
        List<Company> companies = getAllCompanies();
        companies.sort((c1, c2) -> {
            double revenue1 = calculateTotalRevenue(c1.getId());
            double revenue2 = calculateTotalRevenue(c2.getId());
            return Double.compare(revenue2, revenue1);
        });
        return companies;
    }


    private void validateCompany(Company company) {
        if (company.getName() == null || company.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
        if (company.getAddress() == null || company.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Company address cannot be null or empty");
        }
        if (company.getRevenue() < 0) {
            throw new IllegalArgumentException("Company revenue cannot be negative");
        }
    }
}
