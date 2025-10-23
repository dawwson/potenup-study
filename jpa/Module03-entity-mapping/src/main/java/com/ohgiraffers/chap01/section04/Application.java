package com.ohgiraffers.chap01.section04;

import com.ohgiraffers.chap01.section04.model.Customer;
import com.ohgiraffers.chap01.section04.model.Delivery;
import com.ohgiraffers.chap01.section04.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Customer customer = new Customer("테스트 고객");
            Order order = new Order();
            customer.addOrder(order);

            Delivery delivery = new Delivery("서울시 송파구", "READY");
            order.setDelivery(delivery);

            em.persist(customer);
            tx.commit();
            em.clear();

            List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();

            for (Customer c : customers) {
                System.out.println("고객 이름 : " + c.getName());
                System.out.println("주문 수 : " + c.getOrders().size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
