package org.example.tests;

import org.example.entity.Building;
import org.example.entity.Employee;
import org.example.service.implService.EmployeeServiceImpl;

import java.util.List;
import java.util.Map;

public class EmployeeServiceTest {

    private final EmployeeServiceImpl employeeService;

    public EmployeeServiceTest() {
        TestDataSetup.insertTestData();
        this.employeeService = new EmployeeServiceImpl();
    }

    public void testSortEmployeesByName() {
        List<Employee> sortedEmployees = employeeService.sortEmployeesByName();
        if (sortedEmployees.isEmpty()) {
            System.err.println("Test failed: No employees found");
        } else {
            System.out.println("Test passed: Employees sorted by name");
            sortedEmployees.forEach(employee -> System.out.println("Employee: " + employee.getName()));
        }
    }

    public void testSortEmployeesByBuildingCount() {
        List<Employee> sortedEmployees = employeeService.sortEmployeesByBuildingCount();
        if (sortedEmployees.isEmpty()) {
            System.err.println("Test failed: No employees found");
        } else {
            System.out.println("Test passed: Employees sorted by building count");
            sortedEmployees.forEach(employee ->
                    System.out.println("Employee: " + employee.getName() + ", Buildings: " + employee.getBuildingsCount()));
        }
    }

    public void testGetBuildingsByEmployeeForCompany() {
        try {
            int companyId = 1;
            Map<Employee, List<Building>> employeeBuildings = employeeService.getBuildingsByEmployeeForCompany(companyId);

            if (employeeBuildings.isEmpty()) {
                System.err.println("Test failed: No employees or buildings found for company ID " + companyId);
            } else {
                System.out.println("Test passed: Found buildings by employees for company ID " + companyId);
                employeeBuildings.forEach((employee, buildings) -> {
                    System.out.println("Employee: " + employee.getName());
                    buildings.forEach(building -> System.out.println("  - Building: " + building.getAddress()));
                });
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EmployeeServiceTest test = new EmployeeServiceTest();
        test.testSortEmployeesByName();
        test.testSortEmployeesByBuildingCount();
        test.testGetBuildingsByEmployeeForCompany();
    }
}
