package org.example.tests;

import org.example.entity.Resident;
import org.example.service.implService.ResidentServiceImpl;

import java.util.List;

public class ResidentServiceTest {

    private final ResidentServiceImpl residentService;

    public ResidentServiceTest() {
        TestDataSetup.insertTestData();
        this.residentService = new ResidentServiceImpl();
    }

    public void testSortResidentsByName() {
        List<Resident> sortedResidents = residentService.sortResidentsByName();
        if (sortedResidents.isEmpty()) {
            System.err.println("Test failed: No residents found");
        } else {
            System.out.println("Test passed: Residents sorted by name");
            sortedResidents.forEach(resident -> System.out.println("Resident: " + resident.getName()));
        }
    }

    public void testSortResidentsByAge() {
        List<Resident> sortedResidents = residentService.sortResidentsByAge();
        if (sortedResidents.isEmpty()) {
            System.err.println("Test failed: No residents found");
        } else {
            System.out.println("Test passed: Residents sorted by age");
            sortedResidents.forEach(resident -> System.out.println("Resident: " + resident.getName() + ", Age: " + resident.getAge()));
        }
    }

    public static void main(String[] args) {
        ResidentServiceTest test = new ResidentServiceTest();
        test.testSortResidentsByName();
        test.testSortResidentsByAge();
    }
}
