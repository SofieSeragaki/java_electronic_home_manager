package org.example.service;

import org.example.entity.Apartment;
import org.example.entity.Building;
import org.example.entity.Resident;

import java.util.List;

public interface BuildingService {
    Building getBuildingById(int id);
    void saveBuilding(Building building);
    void updateBuilding(Building building);
    void deleteBuildingById(int id);
    List<Building> getAllBuildings();
    void reassignBuildings(int employeeId);
    List<Building> getAllBuildingsByEmployee(int employeeId);
    void setMonthlyFeeForBuilding(int buildingId, double fee);
    List<Apartment> getApartmentsByBuildingId(int buildingId);
    List<Resident> getResidentsByBuildingId(int buildingId);
}
