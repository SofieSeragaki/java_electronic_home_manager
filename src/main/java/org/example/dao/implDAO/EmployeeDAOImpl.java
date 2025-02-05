package org.example.dao.implDAO;

import org.example.configuration.SessionFactoryUtil;
import org.example.dao.EmployeeDAO;
import org.example.entity.Employee;
import org.example.exception.EntityNotFoundException;
import org.hibernate.Session;

import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public Employee findById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Employee employee = session.get(Employee.class, id);
            if (employee == null) {
                throw new EntityNotFoundException("Employee with ID " + id + " not found");
            }
            return employee;
        }
    }

    @Override
    public void save(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee == null) {
                throw new EntityNotFoundException("Employee with ID " + id + " not found for deletion");
            }
            session.delete(employee);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Employee> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employee", Employee.class).list();
        }
    }
}
