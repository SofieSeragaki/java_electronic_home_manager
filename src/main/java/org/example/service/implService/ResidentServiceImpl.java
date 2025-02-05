package org.example.service.implService;

import org.example.dao.ResidentDAO;
import org.example.dao.implDAO.ResidentDAOImpl;
import org.example.entity.Resident;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.service.ResidentService;

import java.util.Comparator;
import java.util.List;

public class ResidentServiceImpl implements ResidentService {

    private final ResidentDAO residentDAO;

    public ResidentServiceImpl() {
        this.residentDAO = new ResidentDAOImpl();
    }

    @Override
    public Resident getResidentById(int id) {
        Resident resident = residentDAO.findById(id);
        if (resident == null) {
            throw new EntityNotFoundException("Resident with ID " + id + " not found");
        }
        return resident;
    }

    @Override
    public void saveResident(Resident resident) {
        validateResident(resident);
        residentDAO.save(resident);
    }

    @Override
    public void updateResident(Resident resident) {
        validateResident(resident);
        if (residentDAO.findById(resident.getId()) == null) {
            throw new EntityNotFoundException("Resident with ID " + resident.getId() + " not found for update");
        }
        residentDAO.update(resident);
    }

    @Override
    public void deleteResidentById(int id) {
        if (residentDAO.findById(id) == null) {
            throw new EntityNotFoundException("Resident with ID " + id + " not found for deletion");
        }
        residentDAO.deleteById(id);
    }

    @Override
    public List<Resident> getAllResidents() {
        return residentDAO.findAll();
    }

    @Override
    public List<Resident> sortResidentsByName() {
        List<Resident> residents = residentDAO.findAll();
        residents.sort(Comparator.comparing(Resident::getName, String.CASE_INSENSITIVE_ORDER));
        return residents;
    }

    @Override
    public List<Resident> sortResidentsByAge() {
        List<Resident> residents = residentDAO.findAll();
        residents.sort(Comparator.comparingInt(Resident::getAge));
        return residents;
    }

    /**
     * metod za validirane
     */
    private void validateResident(Resident resident) {
        if (resident.getName() == null || resident.getName().trim().isEmpty()) {
            throw new ValidationException("Resident name cannot be null or empty");
        }
        if (resident.getAge() <= 0) {
            throw new ValidationException("Resident age must be greater than 0");
        }
        if (resident.getApartment() == null) {
            throw new ValidationException("Resident must be associated with an apartment");
        }
    }
}
