package com.ohgiraffers.valueobject.chap02.mission.b_middle;

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
            Order order = new Order("라떼");
            OrderOption option1 = new OrderOption("시럽 추가", 1000);
            OrderOption option2 = new OrderOption("우유 변경", 2000);
            OrderOption option3 = new OrderOption("얼음 많이", 500);
            order.addOption(option1);
            order.addOption(option2);
            order.addOption(option3);

            int totalPrice = order.getTotalOptionPrice();
            System.out.println("Total Price : " + totalPrice);

            em.persist(order);


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
