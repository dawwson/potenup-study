package com.ohgiraffers.valueobject.chap01.section02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            StayPeriod stayPeriod = new StayPeriod(
                    LocalDate.of(2025, 8, 1),
                    LocalDate.of(2025, 8, 3)
            );

            GuestCount guestCount = new GuestCount(2);
            Reservation reservation = new Reservation(
                    "김철수", "101호", guestCount, stayPeriod, 50000
            );

            em.persist(stayPeriod);
            tx.commit();
            em.close();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
