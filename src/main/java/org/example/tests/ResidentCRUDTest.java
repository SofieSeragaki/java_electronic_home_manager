package org.example.tests;

import org.example.entity.Apartment;
import org.example.entity.Resident;
import org.example.exception.EntityNotFoundException;
import org.example.service.implService.ApartmentServiceImpl;
import org.example.service.implService.ResidentServiceImpl;

import java.util.List;

public class ResidentCRUDTest {

    private final ResidentServiceImpl residentService;
    private final ApartmentServiceImpl apartmentService;

    public ResidentCRUDTest() {
        TestDataSetup.insertTestData();
        this.residentService = new ResidentServiceImpl();
        this.apartmentService = new ApartmentServiceImpl();
    }

    public void testCreateResident() {
        try {
            // excisting
            Apartment apartment = apartmentService.getApartmentById(1);
            if (apartment == null) {
                throw new EntityNotFoundException("Apartment should exist");
            }

            // create
            Resident resident = new Resident();
            resident.setName("Petko Petkov");
            resident.setAge(30);
            resident.setHasPet(false);
            resident.setApartment(apartment);

            // save
            residentService.saveResident(resident);

            // test
            Resident createdResident = residentService.getResidentById(resident.getId());
            if (createdResident != null && "John Doe".equals(createdResident.getName())) {
                System.out.println("Test passed: Resident created successfully.");
            } else {
                System.err.println("Test failed: Resident creation failed.");
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testUpdateResident() {
        try {
            // excisted
            Resident resident = residentService.getResidentById(1);
            if (resident == null) {
                throw new EntityNotFoundException("Resident should exist");
            }

            // update
            resident.setName("Petko Petkov");
            resident.setAge(28);
            resident.setHasPet(true);

            residentService.updateResident(resident);

            // test
            Resident updatedResident = residentService.getResidentById(1);
            if ("Petko Petkov".equals(updatedResident.getName()) && updatedResident.getAge() == 28 && updatedResident.isHasPet()) {
                System.out.println("Test passed: Resident updated successfully.");
            } else {
                System.err.println("Test failed: Resident update failed.");
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testDeleteResident() {
        try {
            // excisted
            Resident resident = residentService.getResidentById(1);
            if (resident == null) {
                throw new EntityNotFoundException("Resident should exist");
            }

            // delete
            residentService.deleteResidentById(1);

            // test
            try {
                residentService.getResidentById(1);
                System.err.println("Test failed: Resident was not deleted.");
            } catch (EntityNotFoundException e) {
                System.out.println("Test passed: Resident deleted successfully.");
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public void testGetAllResidents() {
        try {
            // all residents
            List<Resident> residents = residentService.getAllResidents();
            if (residents != null && !residents.isEmpty()) {
                System.out.println("Test passed: Retrieved all residents.");
            } else {
                System.err.println("Test failed: Residents list is empty or null.");
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ResidentCRUDTest test = new ResidentCRUDTest();
        test.testCreateResident();
        test.testUpdateResident();
        test.testDeleteResident();
        test.testGetAllResidents();
    }
}
