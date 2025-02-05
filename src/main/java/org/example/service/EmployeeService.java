package org.example.service;

import org.example.entity.Building;
import org.example.entity.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee getEmployeeById(int id);
    void saveEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void deleteEmployeeById(int id);
    List<Employee> getAllEmployees();
    List<Employee> sortEmployeesByName();
    List<Employee> sortEmployeesByBuildingCount();
    Map<Employee, List<Building>> getBuildingsByEmployeeForCompany(int companyId); // Нов метод
}
