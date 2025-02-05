package org.example.configuration;

import org.example.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionFactoryUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                //Hibernate config
                Configuration configuration = new Configuration();

                // register
                configuration.addAnnotatedClass(Apartment.class);
                configuration.addAnnotatedClass(Building.class);
                configuration.addAnnotatedClass(Company.class);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(Resident.class);
                configuration.addAnnotatedClass(Payment.class);

                //ServiceRegistry
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                //SessionFactory
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize SessionFactory");
            }
        }
        return sessionFactory;
    }
}
