package org.example.service;

import org.example.entity.Apartment;

import java.util.List;

public interface ApartmentService {
    Apartment getApartmentById(int id);
    void saveApartment(Apartment apartment);
    void updateApartment(Apartment apartment);
    void deleteApartmentById(int id);
    List<Apartment> getAllApartments();
    double calculateMonthlyFee(int apartmentId);
}
