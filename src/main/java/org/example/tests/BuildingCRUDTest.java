package org.example.tests;

import org.example.entity.Building;
import org.example.entity.Company;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.service.implService.BuildingServiceImpl;
import org.example.service.implService.CompanyServiceImpl;

import java.util.List;

public class BuildingCRUDTest {

    private BuildingServiceImpl buildingService;
    private CompanyServiceImpl companyService;

    public BuildingCRUDTest() {
        TestDataSetup.insertTestData();
        buildingService = new BuildingServiceImpl();
        companyService = new CompanyServiceImpl();
    }

    public void testCreateBuilding() {
        // company connected to building
        Company company = companyService.getCompanyById(1); // Poor Company

        Building building = new Building();
        building.setAddress("New Building Address");
        building.setFloors(4);
        building.setApartmentsCount(8);
        building.setTotalArea(800.0);
        building.setCommonArea(100.0);
        building.setCompany(company);

        try {
            // sgrada
            buildingService.saveBuilding(building);

            Building createdBuilding = buildingService.getBuildingById(building.getId());
            if (createdBuilding == null || !createdBuilding.getAddress().equals("New Building Address")) {
                System.err.println("Test failed: Building creation failed.");
            } else {
                System.out.println("Test passed: Building created successfully.");
            }
        } catch (ValidationException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testUpdateBuilding() {
        try {
            Building building = buildingService.getBuildingById(1); // Вземаме съществуваща сграда
            if (building == null) {
                System.err.println("Test failed: Building not found.");
                return;
            }

            // update
            building.setAddress("Updated Address");
            building.setFloors(6);

            buildingService.updateBuilding(building);

            // test
            Building updatedBuilding = buildingService.getBuildingById(2);
            if (updatedBuilding != null && updatedBuilding.getAddress().equals("Updated Address") && updatedBuilding.getFloors() == 6) {
                System.out.println("Test passed: Building updated successfully.");
            } else {
                System.err.println("Test failed: Building update failed.");
            }
        } catch (EntityNotFoundException | ValidationException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testDeleteBuilding() {
        try {
            Building building = buildingService.getBuildingById(1); //excisting building
            if (building == null) {
                System.err.println("Test failed: Building not found.");
                return;
            }

            // Изтриване на сградата
            buildingService.deleteBuildingById(2);

            // Проверка: дали сградата е изтрита
            try {
                buildingService.getBuildingById(2);
                System.err.println("Test failed: Building was not deleted.");
            } catch (EntityNotFoundException e) {
                System.out.println("Test passed: Building deleted successfully.");
            }
        } catch (EntityNotFoundException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testGetAllBuildings() {
        List<Building> buildings = buildingService.getAllBuildings();
        if (buildings == null || buildings.isEmpty()) {
            System.err.println("Test failed: No buildings found.");
        } else {
            System.out.println("Test passed: Retrieved all buildings successfully.");
        }
    }

    public void testSetMonthlyFeeForBuilding() {
        try {
            Building building = buildingService.getBuildingById(22); // Вземаме съществуваща сграда
            if (building == null) {
                System.err.println("Test failed: Building not found.");
                return;
            }

            // Задаване на месечна такса
            double fee = 120.50;
            buildingService.setMonthlyFeeForBuilding(22, fee);

            // Проверка: дали е зададена успешно
            Building updatedBuilding = buildingService.getBuildingById(1);
            if (updatedBuilding != null && updatedBuilding.getMonthlyFee() == fee) {
                System.out.println("Test passed: Monthly fee set successfully.");
            } else {
                System.err.println("Test failed: Monthly fee setting failed.");
            }
        } catch (ValidationException | EntityNotFoundException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BuildingCRUDTest test = new BuildingCRUDTest();
        test.testCreateBuilding();
        test.testUpdateBuilding();
        test.testDeleteBuilding();
        test.testGetAllBuildings();
        test.testSetMonthlyFeeForBuilding();
    }
}
