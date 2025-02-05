package org.example.dao;

import org.example.entity.Employee;
import java.util.List;

public interface EmployeeDAO {
    Employee findById(int id);
    void save(Employee employee);
    void update(Employee employee);
    void deleteById(int id);
    List<Employee> findAll();
}
