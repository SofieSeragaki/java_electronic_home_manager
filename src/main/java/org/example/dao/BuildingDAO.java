package org.example.dao;

import org.example.entity.Building;
import java.util.List;

public interface BuildingDAO {
    Building findById(int id);
    void save(Building building);
    void update(Building building);
    void deleteById(int id);
    List<Building> findAll();
    List<Building> findBuildingsByEmployeeId(int employeeId); // Нов метод
}
