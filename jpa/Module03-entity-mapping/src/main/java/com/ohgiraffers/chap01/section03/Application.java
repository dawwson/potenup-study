package com.ohgiraffers.chap01.section03;

import com.ohgiraffers.chap01.section03.model.Delivery;
import com.ohgiraffers.chap01.section03.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Order order = new Order(2);
            Delivery delivery = new Delivery("서울시 강남구", "READY");

            order.setDelivery(delivery);

            em.persist(order);
            tx.commit();
            Order foundOrder = em.find(Order.class, order.getId());

            System.out.println(foundOrder);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
