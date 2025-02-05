package org.example.dao;

import org.example.entity.Company;
import java.util.List;

public interface CompanyDAO {
    Company findById(int id);
    void save(Company company);
    void update(Company company);
    void deleteById(int id);
    List<Company> findAll();
}
