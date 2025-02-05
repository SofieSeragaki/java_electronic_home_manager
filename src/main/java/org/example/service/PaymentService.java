package org.example.service;

import org.example.entity.Payment;

import java.util.List;

public interface PaymentService {
    void save(Payment payment);
    void update(Payment payment);
    void deleteById(int id);
    Payment findById(int id);
    List<Payment> findAll();
    void generateMonthlyPayments(int buildingId);
    List<Payment> findPaymentsByBuildingId(int buildingId);
    List<Payment> findPaymentsByApartmentId(int apartmentId);

    double calculateTotalAmountForCompany(int companyId);
    double calculatePaidAmountForCompany(int companyId);
    double calculateTotalAmountForBuilding(int buildingId);
    double calculatePaidAmountForBuilding(int buildingId);
    double calculateTotalAmountForEmployee(int employeeId);
    double calculatePaidAmountForEmployee(int employeeId);
}
