package org.example.tests;

import org.example.configuration.SessionFactoryUtil;
import org.hibernate.Session;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            System.out.println("Connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
