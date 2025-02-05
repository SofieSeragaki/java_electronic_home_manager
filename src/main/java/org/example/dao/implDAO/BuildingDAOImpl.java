package org.example.dao.implDAO;

import org.example.configuration.SessionFactoryUtil;
import org.example.dao.BuildingDAO;
import org.example.entity.Building;
import org.example.exception.EntityNotFoundException;
import org.hibernate.Session;

import java.util.List;

public class BuildingDAOImpl implements BuildingDAO {

    @Override
    public Building findById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Building building = session.get(Building.class, id);
            if (building == null) {
                throw new EntityNotFoundException("Building with ID " + id + " not found");
            }
            return building;
        }
    }

    @Override
    public void save(Building building) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(building);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Building building) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(building);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Building building = session.get(Building.class, id);
            if (building == null) {
                throw new EntityNotFoundException("Building with ID " + id + " not found for deletion");
            }
            session.delete(building);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Building> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Building", Building.class).list();
        }
    }

    @Override
    public List<Building> findBuildingsByEmployeeId(int employeeId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Building b where b.employee.id = :employeeId", Building.class)
                    .setParameter("employeeId", employeeId)
                    .list();
        }
    }
}
