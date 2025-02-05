package org.example.tests;

import org.example.entity.Apartment;
import org.example.entity.Resident;
import org.example.service.implService.BuildingServiceImpl;

import java.util.List;

public class BuildingDetailsTest {

    private final BuildingServiceImpl buildingService;

    public BuildingDetailsTest() {
        TestDataSetup.insertTestData();
        this.buildingService = new BuildingServiceImpl();
    }

    public void testGetApartmentsByBuildingId() {
        try {
            List<Apartment> apartments = buildingService.getApartmentsByBuildingId(1); // ID на сградата
            if (!apartments.isEmpty()) {
                System.out.println("Test passed: Found " + apartments.size() + " apartments in the building.");
            } else {
                System.err.println("Test failed: No apartments found.");
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testGetResidentsByBuildingId() {
        try {
            List<Resident> residents = buildingService.getResidentsByBuildingId(1); // ID на сградата
            if (!residents.isEmpty()) {
                System.out.println("Test passed: Found " + residents.size() + " residents in the building.");
            } else {
                System.err.println("Test failed: No residents found.");
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BuildingDetailsTest test = new BuildingDetailsTest();
        test.testGetApartmentsByBuildingId();
        test.testGetResidentsByBuildingId();
    }
}
