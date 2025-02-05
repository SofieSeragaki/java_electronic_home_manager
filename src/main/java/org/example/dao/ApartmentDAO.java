package org.example.dao;

import org.example.entity.Apartment;
import java.util.List;

public interface ApartmentDAO {
    Apartment findById(int id);
    void save(Apartment apartment);
    void update(Apartment apartment);
    void deleteById(int id);
    List<Apartment> findAll();
}
