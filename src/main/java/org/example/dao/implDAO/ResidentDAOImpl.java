package org.example.dao.implDAO;

import org.example.configuration.SessionFactoryUtil;
import org.example.dao.ResidentDAO;
import org.example.entity.Resident;
import org.example.exception.EntityNotFoundException;
import org.hibernate.Session;

import java.util.List;

public class ResidentDAOImpl implements ResidentDAO {

    @Override
    public Resident findById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Resident resident = session.get(Resident.class, id);
            if (resident == null) {
                throw new EntityNotFoundException("Resident with ID " + id + " not found");
            }
            return resident;
        }
    }

    @Override
    public void save(Resident resident) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(resident);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Resident resident) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(resident);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Resident resident = session.get(Resident.class, id);
            if (resident == null) {
                throw new EntityNotFoundException("Resident with ID " + id + " not found for deletion");
            }
            session.delete(resident);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Resident> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Resident", Resident.class).list();
        }
    }
}
