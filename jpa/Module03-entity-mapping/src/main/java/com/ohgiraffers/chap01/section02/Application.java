package com.ohgiraffers.chap01.section02;

import com.ohgiraffers.chap01.section02.model.Customer;
import com.ohgiraffers.chap01.section02.model.Order;
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
            Customer customer = new Customer("John Smith");

            Order order = new Order();
            customer.addOrder(order);
            em.persist(customer);

            Customer foundCustomer = em.find(Customer.class, customer.getId());
            System.out.println("주문 목록 : ");
            foundCustomer.getOrders().forEach(System.out::println);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
