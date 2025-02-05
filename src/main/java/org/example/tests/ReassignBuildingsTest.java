package org.example.tests;

import org.example.entity.Building;
import org.example.entity.Employee;
import org.example.exception.EntityNotFoundException;
import org.example.service.implService.BuildingServiceImpl;
import org.example.service.implService.EmployeeServiceImpl;

import java.util.List;

public class ReassignBuildingsTest {

    private final BuildingServiceImpl buildingService;
    private final EmployeeServiceImpl employeeService;

    public ReassignBuildingsTest() {
        TestDataSetup.insertTestData(); // Зареждане на тестови данни
        this.buildingService = new BuildingServiceImpl();
        this.employeeService = new EmployeeServiceImpl();
    }

    public void testReassignBuildingsAfterEmployeeFired() {
        try {
            // employee Rich Company
            List<Employee> employees = employeeService.getAllEmployees();
            Employee employeeToFire = employees.stream()
                    .filter(emp -> "Misho Mishev".equals(emp.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Employee not found"));

            Employee remainingEmployee = employees.stream()
                    .filter(emp -> "Vladi Vladov".equals(emp.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Remaining employee not found"));

            // test if both have 1 building
            List<Building> mishoBuildings = buildingService.getAllBuildingsByEmployee(employeeToFire.getId());
            if (mishoBuildings.size() == 1) {
                System.out.println("Test passed: Misho has 1 building initially.");
            } else {
                System.err.println("Test failed: Misho does not have the expected number of buildings.");
            }

            List<Building> vladiBuildings = buildingService.getAllBuildingsByEmployee(remainingEmployee.getId());
            if (vladiBuildings.size() == 1) {
                System.out.println("Test passed: Vladi has 1 building initially.");
            } else {
                System.err.println("Test failed: Vladi does not have the expected number of buildings.");
            }

            // fired misho
            employeeService.deleteEmployeeById(employeeToFire.getId());
            buildingService.reassignBuildings(employeeToFire.getId());

            // test if building is moved to vladi (2)
            List<Building> updatedVladiBuildings = buildingService.getAllBuildingsByEmployee(remainingEmployee.getId());
            if (updatedVladiBuildings.size() == 2) {
                System.out.println("Test passed: Vladi now has 2 buildings.");
            } else {
                System.err.println("Test failed: Vladi does not have the expected number of buildings.");
            }

            // test if misho had building
            try {
                buildingService.getAllBuildingsByEmployee(employeeToFire.getId());
                System.err.println("Test failed: Misho still has buildings after being fired.");
            } catch (EntityNotFoundException e) {
                System.out.println("Test passed: Misho has no buildings after being fired.");
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ReassignBuildingsTest test = new ReassignBuildingsTest();
        test.testReassignBuildingsAfterEmployeeFired();
    }
}
