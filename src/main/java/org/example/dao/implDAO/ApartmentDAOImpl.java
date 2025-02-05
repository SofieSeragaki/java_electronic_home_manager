package org.example.dao.implDAO;

import org.example.configuration.SessionFactoryUtil;
import org.example.dao.ApartmentDAO;
import org.example.entity.Apartment;
import org.example.exception.EntityNotFoundException;
import org.hibernate.Session;

import java.util.List;

public class ApartmentDAOImpl implements ApartmentDAO {

    @Override
    public Apartment findById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Apartment apartment = session.get(Apartment.class, id);
            if (apartment == null) {
                throw new EntityNotFoundException("Apartment with ID " + id + " not found");
            }
            return apartment;
        }
    }

    @Override
    public void save(Apartment apartment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(apartment);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Apartment apartment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(apartment);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Apartment apartment = session.get(Apartment.class, id);
            if (apartment == null) {
                throw new EntityNotFoundException("Apartment with ID " + id + " not found for deletion");
            }
            session.delete(apartment);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Apartment> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Apartment", Apartment.class).list();
        }
    }
}
