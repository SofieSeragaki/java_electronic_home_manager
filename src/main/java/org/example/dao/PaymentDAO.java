package org.example.dao;

import org.example.entity.Payment;
import java.util.List;

public interface PaymentDAO {
    void save(Payment payment);
    void update(Payment payment);
    void deleteById(int id);
    Payment findById(int id);
    List<Payment> findAll();
}
