package org.example.service.implService;

import org.example.dao.ApartmentDAO;
import org.example.dao.implDAO.ApartmentDAOImpl;
import org.example.entity.Apartment;
import org.example.entity.Resident;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.service.ApartmentService;

import java.util.List;

public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentDAO apartmentDAO;

    private static final double BASE_RATE_PER_SQUARE_METER = 20.0;
    private static final double RESIDENT_FEE = 5.0;
    private static final double PET_FEE = 10.0;

    public ApartmentServiceImpl() {
        this.apartmentDAO = new ApartmentDAOImpl();
    }

    @Override
    public Apartment getApartmentById(int id) {
        Apartment apartment = apartmentDAO.findById(id);
        if (apartment == null) {
            throw new EntityNotFoundException("Apartment with ID " + id + " not found");
        }
        return apartment;
    }

    @Override
    public void saveApartment(Apartment apartment) {
        validateApartment(apartment);
        apartmentDAO.save(apartment);
    }

    @Override
    public void updateApartment(Apartment apartment) {
        validateApartment(apartment);
        apartmentDAO.update(apartment);
    }

    @Override
    public void deleteApartmentById(int id) {
        if (apartmentDAO.findById(id) == null) {
            throw new EntityNotFoundException("Apartment with ID " + id + " not found for deletion");
        }
        apartmentDAO.deleteById(id);
    }

    @Override
    public List<Apartment> getAllApartments() {
        return apartmentDAO.findAll();
    }

    @Override
    public double calculateMonthlyFee(int apartmentId) {
        Apartment apartment = apartmentDAO.findById(apartmentId);
        if (apartment == null) {
            throw new EntityNotFoundException("Apartment with ID " + apartmentId + " not found");
        }

        double baseFee = apartment.getArea() * BASE_RATE_PER_SQUARE_METER;

        double residentFee = 0.0;
        List<Resident> residents = apartment.getResidents();
        for (Resident resident : residents) {
            if (resident.getAge() > 7) {
                residentFee += RESIDENT_FEE;
            }
        }

        double petFee = apartment.isHasPet() ? PET_FEE : 0.0;

        return baseFee + residentFee + petFee;
    }

    /**
     * metod za validaciq
     */
    private void validateApartment(Apartment apartment) {
        if (apartment.getNumber() <= 0) {
            throw new ValidationException("Apartment number must be greater than 0");
        }
        if (apartment.getArea() <= 0) {
            throw new ValidationException("Apartment area must be greater than 0");
        }
        if (apartment.getBuilding() == null) {
            throw new ValidationException("Apartment must be associated with a building");
        }
    }
}
