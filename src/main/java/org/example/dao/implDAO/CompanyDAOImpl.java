package org.example.dao.implDAO;

import org.example.configuration.SessionFactoryUtil;
import org.example.dao.CompanyDAO;
import org.example.entity.Company;
import org.example.exception.EntityNotFoundException;
import org.hibernate.Session;

import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    @Override
    public Company findById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Company company = session.get(Company.class, id);
            if (company == null) {
                throw new EntityNotFoundException("Company with ID " + id + " not found");
            }
            return company;
        }
    }

    @Override
    public void save(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(company);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(company);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Company company = session.get(Company.class, id);
            if (company == null) {
                throw new EntityNotFoundException("Company with ID " + id + " not found for deletion");
            }
            session.delete(company);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Company> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Company", Company.class).list();
        }
    }
}
