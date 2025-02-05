package org.example.dao;

import org.example.entity.Resident;
import java.util.List;

public interface ResidentDAO {
    Resident findById(int id);
    void save(Resident resident);
    void update(Resident resident);
    void deleteById(int id);
    List<Resident> findAll();
}
