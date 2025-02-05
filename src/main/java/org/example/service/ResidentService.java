package org.example.service;

import org.example.entity.Resident;

import java.util.List;

public interface ResidentService {
    Resident getResidentById(int id);
    void saveResident(Resident resident);
    void updateResident(Resident resident);
    void deleteResidentById(int id);
    List<Resident> getAllResidents();
    List<Resident> sortResidentsByName();
    List<Resident> sortResidentsByAge();
}
