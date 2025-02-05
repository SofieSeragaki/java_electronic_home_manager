package org.example.tests;

import org.example.entity.Company;
import org.example.entity.Employee;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.service.implService.CompanyServiceImpl;
import org.example.service.implService.EmployeeServiceImpl;

import java.util.List;

public class EmployeeCRUDTest {

    private final EmployeeServiceImpl employeeService;
    private final CompanyServiceImpl companyService;

    public EmployeeCRUDTest() {
        TestDataSetup.insertTestData(); // Зареждане на тестови данни
        this.employeeService = new EmployeeServiceImpl();
        this.companyService = new CompanyServiceImpl();
    }

    public void testCreateEmployee() {
        try {
            Company company = companyService.getCompanyById(1);

            Employee employee = new Employee();
            employee.setName("John Worker");
            employee.setContact("john.worker@example.com");
            employee.setCompany(company);

            employeeService.saveEmployee(employee);

            // test
            Employee createdEmployee = employeeService.getEmployeeById(employee.getId());
            if (createdEmployee != null && "John Worker".equals(createdEmployee.getName())) {
                System.out.println("Test passed: Employee created successfully.");
            } else {
                System.err.println("Test failed: Employee creation failed.");
            }
        } catch (ValidationException | EntityNotFoundException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testUpdateEmployee() {
        try {
            // excisting
            Employee employee = employeeService.getEmployeeById(1);

            // update
            employee.setName("Jane Worker");
            employee.setContact("jane.worker@example.com");

            employeeService.updateEmployee(employee);

            // test
            Employee updatedEmployee = employeeService.getEmployeeById(1);
            if (updatedEmployee != null && "Jane Worker".equals(updatedEmployee.getName())) {
                System.out.println("Test passed: Employee updated successfully.");
            } else {
                System.err.println("Test failed: Employee update failed.");
            }
        } catch (ValidationException | EntityNotFoundException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testDeleteEmployee() {
        try {
            Employee employee = employeeService.getEmployeeById(1);

            // delete
            employeeService.deleteEmployeeById(1);

            try {
                employeeService.getEmployeeById(1);
                System.err.println("Test failed: Employee was not deleted.");
            } catch (EntityNotFoundException e) {
                System.out.println("Test passed: Employee deleted successfully.");
            }
        } catch (EntityNotFoundException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testGetAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees != null && !employees.isEmpty()) {
            System.out.println("Test passed: Employees retrieved successfully.");
        } else {
            System.err.println("Test failed: No employees found.");
        }
    }

    public static void main(String[] args) {
        EmployeeCRUDTest test = new EmployeeCRUDTest();
        test.testCreateEmployee();
        test.testUpdateEmployee();
        test.testDeleteEmployee();
        test.testGetAllEmployees();
    }
}
