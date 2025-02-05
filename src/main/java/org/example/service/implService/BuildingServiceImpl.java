package org.example.service.implService;

import org.example.dao.ApartmentDAO;
import org.example.dao.BuildingDAO;
import org.example.dao.EmployeeDAO;
import org.example.dao.implDAO.ApartmentDAOImpl;
import org.example.dao.implDAO.BuildingDAOImpl;
import org.example.dao.implDAO.EmployeeDAOImpl;
import org.example.entity.Apartment;
import org.example.entity.Building;
import org.example.entity.Employee;
import org.example.entity.Resident;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.service.BuildingService;

import java.util.List;
import java.util.stream.Collectors;

public class BuildingServiceImpl implements BuildingService {

    private final BuildingDAO buildingDAO;
    private final EmployeeDAO employeeDAO;
    private final ApartmentDAO apartmentDAO;

    public BuildingServiceImpl() {
        this.buildingDAO = new BuildingDAOImpl();
        this.employeeDAO = new EmployeeDAOImpl();
        this.apartmentDAO = new ApartmentDAOImpl();
    }

    @Override
    public Building getBuildingById(int id) {
        Building building = buildingDAO.findById(id);
        if (building == null) {
            throw new EntityNotFoundException("Building with ID " + id + " not found");
        }
        return building;
    }

    @Override
    public void saveBuilding(Building building) {
        validateBuilding(building);
        buildingDAO.save(building);
    }

    @Override
    public void updateBuilding(Building building) {
        validateBuilding(building);
        if (buildingDAO.findById(building.getId()) == null) {
            throw new EntityNotFoundException("Building with ID " + building.getId() + " not found");
        }
        buildingDAO.update(building);
    }

    @Override
    public void deleteBuildingById(int id) {
        if (buildingDAO.findById(id) == null) {
            throw new EntityNotFoundException("Building with ID " + id + " not found for deletion");
        }
        buildingDAO.deleteById(id);
    }

    @Override
    public List<Building> getAllBuildings() {
        return buildingDAO.findAll();
    }

    @Override
    public void reassignBuildings(int employeeId) {
        Employee employee = employeeDAO.findById(employeeId);
        if (employee == null) {
            throw new EntityNotFoundException("Employee with ID " + employeeId + " not found");
        }

        List<Building> buildings = buildingDAO.findAll();
        List<Employee> employees = employeeDAO.findAll();

        for (Building building : buildings) {
            if (building.getEmployee() != null && building.getEmployee().getId() == employeeId) {
                Employee reassignedEmployee = employees.stream()
                        .filter(e -> e.getId() != employeeId)
                        .min((e1, e2) -> Integer.compare(e1.getBuildingsCount(), e2.getBuildingsCount()))
                        .orElseThrow(() -> new EntityNotFoundException("No available employees to reassign the building"));

                building.setEmployee(reassignedEmployee);
                buildingDAO.update(building);
            }
        }
    }

    @Override
    public List<Building> getAllBuildingsByEmployee(int employeeId) {
        List<Building> buildings = buildingDAO.findBuildingsByEmployeeId(employeeId);
        if (buildings == null || buildings.isEmpty()) {
            throw new EntityNotFoundException("No buildings found for employee with ID " + employeeId);
        }
        return buildings;
    }

    @Override
    public void setMonthlyFeeForBuilding(int buildingId, double fee) {
        if (fee < 0) {
            throw new ValidationException("Monthly fee cannot be negative");
        }

        Building building = buildingDAO.findById(buildingId);
        if (building == null) {
            throw new EntityNotFoundException("Building with ID " + buildingId + " not found");
        }

        building.setMonthlyFee(fee);
        buildingDAO.update(building);
    }

    @Override
    public List<Apartment> getApartmentsByBuildingId(int buildingId) {
        Building building = getBuildingById(buildingId);
        return building.getApartments();
    }

    @Override
    public List<Resident> getResidentsByBuildingId(int buildingId) {
        List<Apartment> apartments = getApartmentsByBuildingId(buildingId);
        return apartments.stream()
                .flatMap(apartment -> apartment.getResidents().stream())
                .collect(Collectors.toList());
    }

    private void validateBuilding(Building building) {
        if (building.getAddress() == null || building.getAddress().trim().isEmpty()) {
            throw new ValidationException("Building address cannot be null or empty");
        }
        if (building.getFloors() <= 0) {
            throw new ValidationException("Building must have at least one floor");
        }
        if (building.getApartmentsCount() <= 0) {
            throw new ValidationException("Building must have at least one apartment");
        }
        if (building.getTotalArea() <= 0) {
            throw new ValidationException("Building total area must be greater than 0");
        }
        if (building.getCommonArea() < 0) {
            throw new ValidationException("Building common area cannot be negative");
        }
    }
}
