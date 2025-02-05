package org.example.service.implService;

import org.example.dao.BuildingDAO;
import org.example.dao.EmployeeDAO;
import org.example.dao.implDAO.BuildingDAOImpl;
import org.example.dao.implDAO.EmployeeDAOImpl;
import org.example.entity.Building;
import org.example.entity.Employee;
import org.example.exception.EntityNotFoundException;
import org.example.service.EmployeeService;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;
    private final BuildingDAO buildingDAO;

    public EmployeeServiceImpl() {
        this.employeeDAO = new EmployeeDAOImpl();
        this.buildingDAO = new BuildingDAOImpl();
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = employeeDAO.findById(id);
        if (employee == null) {
            throw new EntityNotFoundException("Employee with ID " + id + " not found");
        }
        return employee;
    }

    @Override
    public void saveEmployee(Employee employee) {
        validateEmployee(employee);
        employeeDAO.save(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        validateEmployee(employee);
        if (employeeDAO.findById(employee.getId()) == null) {
            throw new EntityNotFoundException("Employee with ID " + employee.getId() + " not found");
        }
        employeeDAO.update(employee);
    }

    @Override
    public void deleteEmployeeById(int id) {
        if (employeeDAO.findById(id) == null) {
            throw new EntityNotFoundException("Employee with ID " + id + " not found");
        }
        employeeDAO.deleteById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    @Override
    public List<Employee> sortEmployeesByName() {
        List<Employee> employees = employeeDAO.findAll();
        employees.sort(Comparator.comparing(Employee::getName, String.CASE_INSENSITIVE_ORDER));
        return employees;
    }

    @Override
    public List<Employee> sortEmployeesByBuildingCount() {
        List<Employee> employees = employeeDAO.findAll();
        employees.sort(Comparator.comparingInt(Employee::getBuildingsCount).reversed());
        return employees;
    }

    @Override
    public Map<Employee, List<Building>> getBuildingsByEmployeeForCompany(int companyId) {
        List<Employee> employees = employeeDAO.findAll().stream()
                .filter(employee -> employee.getCompany() != null && employee.getCompany().getId() == companyId)
                .collect(Collectors.toList());

        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No employees found for company with ID " + companyId);
        }

        Map<Employee, List<Building>> result = new HashMap<>();
        for (Employee employee : employees) {
            List<Building> buildings = buildingDAO.findAll().stream()
                    .filter(building -> building.getEmployee() != null
                            && building.getEmployee().getId() == employee.getId()
                            && building.getCompany().getId() == companyId)
                    .collect(Collectors.toList());
            result.put(employee, buildings);
        }

        return result;
    }

    private void validateEmployee(Employee employee) {
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        if (employee.getContact() == null || employee.getContact().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee contact cannot be null or empty");
        }
        if (employee.getCompany() == null) {
            throw new IllegalArgumentException("Employee must be associated with a company");
        }
    }
}
