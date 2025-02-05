package org.example.tests;

import org.example.configuration.SessionFactoryUtil;
import org.example.entity.*;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestDataSetup {

    public static void insertTestData() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // Companies
            Company company1 = new Company();
            company1.setName("Poor Company");
            company1.setAddress("Lyulin Street");
            company1.setRevenue(5000.0);
            session.save(company1);

            Company company2 = new Company();
            company2.setName("Rich Company");
            company2.setAddress("Studentski grad");
            company2.setRevenue(10000.0);
            session.save(company2);

            // Employees
            Employee employee1 = new Employee();
            employee1.setName("Sofie Pays");
            employee1.setContact("sofie556@gmail.com");
            employee1.setCompany(company1);
            session.save(employee1);

            Employee employee2 = new Employee();
            employee2.setName("Misho Mishev");
            employee2.setContact("misho76@gmail.com");
            employee2.setCompany(company2);
            session.save(employee2);

            Employee employee3 = new Employee();
            employee3.setName("Vladi Vladov");
            employee3.setContact("vladi123@gmail.com");
            employee3.setCompany(company2);
            session.save(employee3);

            // Buildings
            Building building1 = createBuilding("Sgrada 1", company1, employee1, 5);
            session.save(building1);

            Building building2 = createBuilding("Sgrada 2", company2, employee2, 5);
            session.save(building2);

            Building building3 = createBuilding("Sgrada 3", company2, employee3, 5);
            session.save(building3);

            // Apartments and Residents
            List<Apartment> allApartments = new ArrayList<>();
            allApartments.addAll(createApartmentsAndResidents(session, building1));
            allApartments.addAll(createApartmentsAndResidents(session, building2));
            allApartments.addAll(createApartmentsAndResidents(session, building3));

            // Payments
            createPaymentsForApartments(session, allApartments);

            session.getTransaction().commit();
        }
    }

    private static Building createBuilding(String address, Company company, Employee employee, int floors) {
        Building building = new Building();
        building.setAddress(address);
        building.setFloors(floors);
        building.setApartmentsCount(floors * 2);
        building.setTotalArea(floors * 200);
        building.setCommonArea(50.0);
        building.setMonthlyFee(100.0);
        building.setCompany(company);
        building.setEmployee(employee);
        return building;
    }

    private static List<Apartment> createApartmentsAndResidents(Session session, Building building) {
        int apartmentNumber = 1;
        List<Apartment> apartments = new ArrayList<>();

        for (int floor = 1; floor <= building.getFloors(); floor++) {
            for (int aptPerFloor = 0; aptPerFloor < 2; aptPerFloor++) {
                Apartment apartment = new Apartment();
                apartment.setNumber(apartmentNumber++);
                apartment.setArea(80.0);
                apartment.setHasPet(false);
                apartment.setBuilding(building);

                session.save(apartment);
                apartments.add(apartment);

                // Добавяне на жители към апартамента
                List<Resident> residents = new ArrayList<>();

                if (apartmentNumber % 3 == 0) { // apartments with dogs
                    residents.add(createResident("Adult 1", 35, true, apartment));
                    residents.add(createResident("Adult 2", 34, true, apartment));
                } else if (apartmentNumber % 5 == 0) { // with kids
                    residents.add(createResident("Parent 1", 40, false, apartment));
                    residents.add(createResident("Parent 2", 38, false, apartment));
                    residents.add(createResident("Child 1", 10, false, apartment));
                    residents.add(createResident("Child 2", 8, false, apartment));
                } else { // Други апартаменти
                    residents.add(createResident("Single Resident", 28, false, apartment));
                }

                for (Resident resident : residents) {
                    session.save(resident);
                }
            }
        }
        return apartments;
    }

    private static Resident createResident(String name, int age, boolean hasPet, Apartment apartment) {
        Resident resident = new Resident();
        resident.setName(name);
        resident.setAge(age);
        resident.setHasPet(hasPet);
        resident.setApartment(apartment);
        return resident;
    }

    private static void createPaymentsForApartments(Session session, List<Apartment> apartments) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        calendar.add(Calendar.MONTH, -1);
        Date previousMonthDate = calendar.getTime();

        for (int i = 0; i < apartments.size(); i++) {
            Apartment apartment = apartments.get(i);

            Payment payment = new Payment();
            payment.setApartment(apartment);
            payment.setBuilding(apartment.getBuilding());
            payment.setCompany(apartment.getBuilding().getCompany());
            payment.setEmployee(apartment.getBuilding().getEmployee());
            payment.setAmount(apartment.getBuilding().getMonthlyFee());

            if (i % 2 == 0) {
                payment.setPaymentDate(currentDate); // Current month payment
                payment.setPaid(true);
            } else {
                payment.setPaymentDate(previousMonthDate); // Previous month payment
                payment.setPaid(true);
            }

            session.save(payment);
        }
    }

    public static void main(String[] args) {
        try {
            insertTestData();
            System.out.println("Test data inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to insert test data.");
        }
    }
}
