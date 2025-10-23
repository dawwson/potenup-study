package com.ohgiraffers.chap01.section01;

import com.ohgiraffers.chap01.section01.model.Customer;
import com.ohgiraffers.chap01.section01.model.Order;
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
            Customer customer = new Customer("홍길동");
            em.persist(customer); // 영속성 컨텍스트 1차 캐싱에 저장

            Order order = new Order(customer);
            em.persist(order);

            tx.commit(); // 트랜잭션 커밋
            em.clear(); // 1차 캐싱 초기화

            Order foundOrder = em.find(Order.class, order.getId());
            System.out.println("customer 조회 이전 : ");
            System.out.println("지연 로딩 테스트 : " + foundOrder); // 이때 연관된 customer 찾음(SELECT)
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
