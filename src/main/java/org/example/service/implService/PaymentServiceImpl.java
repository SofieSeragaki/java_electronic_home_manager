package org.example.service.implService;

import org.example.dao.ApartmentDAO;
import org.example.dao.PaymentDAO;
import org.example.dao.implDAO.ApartmentDAOImpl;
import org.example.dao.implDAO.PaymentDAOImpl;
import org.example.entity.Apartment;
import org.example.entity.Payment;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.service.PaymentService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;
    private final ApartmentDAO apartmentDAO;

    public PaymentServiceImpl() {
        this.paymentDAO = new PaymentDAOImpl();
        this.apartmentDAO = new ApartmentDAOImpl();
    }

    @Override
    public void save(Payment payment) {
        validatePayment(payment);
        paymentDAO.save(payment);
    }

    @Override
    public void update(Payment payment) {
        validatePayment(payment);
        paymentDAO.update(payment);
    }

    @Override
    public void deleteById(int id) {
        Payment existingPayment = paymentDAO.findById(id);
        if (existingPayment == null) {
            throw new EntityNotFoundException("Payment with ID " + id + " not found for deletion");
        }
        paymentDAO.deleteById(id);
    }

    @Override
    public Payment findById(int id) {
        Payment payment = paymentDAO.findById(id);
        if (payment == null) {
            throw new EntityNotFoundException("Payment with ID " + id + " not found");
        }
        return payment;
    }

    @Override
    public List<Payment> findAll() {
        return paymentDAO.findAll();
    }

    @Override
    public void generateMonthlyPayments(int buildingId) {
        List<Apartment> apartments = apartmentDAO.findAll();

        for (Apartment apartment : apartments) {
            if (apartment.getBuilding().getId() != buildingId) {
                continue;
            }

            double monthlyFee = apartment.getBuilding().getMonthlyFee();
            if (monthlyFee <= 0) {
                throw new ValidationException("Building with ID " + buildingId + " has no valid monthly fee set.");
            }

            Payment payment = new Payment();
            payment.setAmount(monthlyFee);
            payment.setPaymentDate(new Date());
            payment.setApartment(apartment);
            payment.setBuilding(apartment.getBuilding());
            payment.setCompany(apartment.getBuilding().getCompany());

            if (apartment.getBuilding().getEmployee() == null) {
                throw new EntityNotFoundException("Building with ID " + buildingId + " has no associated employee.");
            }

            payment.setEmployee(apartment.getBuilding().getEmployee());
            payment.setPaid(false); // Default status is unpaid
            paymentDAO.save(payment);
        }
    }

    @Override
    public List<Payment> findPaymentsByBuildingId(int buildingId) {
        return paymentDAO.findAll().stream()
                .filter(payment -> payment.getBuilding().getId() == buildingId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> findPaymentsByApartmentId(int apartmentId) {
        return paymentDAO.findAll().stream()
                .filter(payment -> payment.getApartment().getId() == apartmentId)
                .collect(Collectors.toList());
    }

    // Calculating total and paid amounts for companies, buildings, and employees
    @Override
    public double calculateTotalAmountForCompany(int companyId) {
        return paymentDAO.findAll().stream()
                .filter(payment -> payment.getCompany().getId() == companyId)
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    @Override
    public double calculatePaidAmountForCompany(int companyId) {
        return paymentDAO.findAll().stream()
                .filter(payment -> payment.getCompany().getId() == companyId && payment.isPaid())
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    @Override
    public double calculateTotalAmountForBuilding(int buildingId) {
        return paymentDAO.findAll().stream()
                .filter(payment -> payment.getBuilding().getId() == buildingId)
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    @Override
    public double calculatePaidAmountForBuilding(int buildingId) {
        return paymentDAO.findAll().stream()
                .filter(payment -> payment.getBuilding().getId() == buildingId && payment.isPaid())
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    @Override
    public double calculateTotalAmountForEmployee(int employeeId) {
        return paymentDAO.findAll().stream()
                .filter(payment -> payment.getEmployee().getId() == employeeId)
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    @Override
    public double calculatePaidAmountForEmployee(int employeeId) {
        return paymentDAO.findAll().stream()
                .filter(payment -> payment.getEmployee().getId() == employeeId && payment.isPaid())
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    private void validatePayment(Payment payment) {
        if (payment.getAmount() <= 0) {
            throw new ValidationException("Payment amount must be greater than 0");
        }
        if (payment.getPaymentDate() == null) {
            throw new ValidationException("Payment date cannot be null");
        }
        if (payment.getCompany() == null) {
            throw new ValidationException("Payment must be associated with a company");
        }
        if (payment.getEmployee() == null) {
            throw new ValidationException("Payment must be associated with an employee");
        }
        if (payment.getBuilding() == null) {
            throw new ValidationException("Payment must be associated with a building");
        }
        if (payment.getApartment() == null) {
            throw new ValidationException("Payment must be associated with an apartment");
        }
    }
}
