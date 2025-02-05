package org.example.dao.implDAO;

import org.example.dao.PaymentDAO;
import org.example.entity.Payment;
import org.example.configuration.SessionFactoryUtil;
import org.example.exception.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public void save(Payment payment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(payment);
            transaction.commit();
        }
    }

    @Override
    public void update(Payment payment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(payment);
            transaction.commit();
        }
    }

    @Override
    public void deleteById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Payment payment = session.get(Payment.class, id);
            if (payment == null) {
                throw new EntityNotFoundException("Payment with ID " + id + " not found for deletion");
            }
            session.delete(payment);
            transaction.commit();
        }
    }

    @Override
    public Payment findById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Payment payment = session.get(Payment.class, id);
            if (payment == null) {
                throw new EntityNotFoundException("Payment with ID " + id + " not found");
            }
            return payment;
        }
    }

    @Override
    public List<Payment> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Payment", Payment.class).list();
        }
    }
}
