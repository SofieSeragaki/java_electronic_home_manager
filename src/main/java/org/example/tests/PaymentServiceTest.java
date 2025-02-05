package org.example.tests;

import org.example.entity.Apartment;
import org.example.entity.Payment;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.service.implService.ApartmentServiceImpl;
import org.example.service.implService.PaymentServiceImpl;

import java.util.List;

public class PaymentServiceTest {

    private final PaymentServiceImpl paymentService;
    private final ApartmentServiceImpl apartmentService;

    public PaymentServiceTest() {
        TestDataSetup.insertTestData();
        this.apartmentService = new ApartmentServiceImpl();
        this.paymentService = new PaymentServiceImpl();
    }

    public void testCalculateTotalAmountForCompany() {
        try {
            double totalAmount = paymentService.calculateTotalAmountForCompany(1); // Компания с ID 1
            System.out.println("Test passed: Total amount for company ID 1 = " + totalAmount);
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testCalculatePaidAmountForCompany() {
        try {
            double paidAmount = paymentService.calculatePaidAmountForCompany(1); // Компания с ID 1
            System.out.println("Test passed: Paid amount for company ID 1 = " + paidAmount);
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testCalculateTotalAmountForBuilding() {
        try {
            double totalAmount = paymentService.calculateTotalAmountForBuilding(1); // Сграда с ID 1
            System.out.println("Test passed: Total amount for building ID 1 = " + totalAmount);
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testCalculatePaidAmountForBuilding() {
        try {
            double paidAmount = paymentService.calculatePaidAmountForBuilding(1); // Сграда с ID 1
            System.out.println("Test passed: Paid amount for building ID 1 = " + paidAmount);
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testCalculateTotalAmountForEmployee() {
        try {
            double totalAmount = paymentService.calculateTotalAmountForEmployee(1); // Служител с ID 1
            System.out.println("Test passed: Total amount for employee ID 1 = " + totalAmount);
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testCalculatePaidAmountForEmployee() {
        try {
            double paidAmount = paymentService.calculatePaidAmountForEmployee(1); // Служител с ID 1
            System.out.println("Test passed: Paid amount for employee ID 1 = " + paidAmount);
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testFindPaymentsByBuilding() {
        List<Payment> payments = paymentService.findPaymentsByBuildingId(1);
        if (payments == null || payments.isEmpty()) {
            System.err.println("Test failed: No payments found for building ID 1.");
        } else {
            System.out.println("Test passed: Found payments for building ID 1 successfully.");
        }
    }

    public void testFindPaymentsByApartment() {
        List<Payment> payments = paymentService.findPaymentsByApartmentId(1);
        if (payments == null || payments.isEmpty()) {
            System.err.println("Test failed: No payments found for apartment ID 1.");
        } else {
            System.out.println("Test passed: Found payments for apartment ID 1 successfully.");
        }
    }

    public static void main(String[] args) {
        PaymentServiceTest test = new PaymentServiceTest();
        test.testCalculateTotalAmountForCompany();
        test.testCalculatePaidAmountForCompany();
        test.testCalculateTotalAmountForBuilding();
        test.testCalculatePaidAmountForBuilding();
        test.testCalculateTotalAmountForEmployee();
        test.testCalculatePaidAmountForEmployee();
        test.testFindPaymentsByBuilding();
        test.testFindPaymentsByApartment();
    }
}
