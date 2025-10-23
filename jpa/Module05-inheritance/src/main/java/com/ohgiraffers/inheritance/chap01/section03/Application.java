package com.ohgiraffers.inheritance.chap01.section03;

import com.ohgiraffers.inheritance.chap01.section03.model.ClothingProductJoined;
import com.ohgiraffers.inheritance.chap01.section03.model.ElectronicProductJoined;
import com.ohgiraffers.inheritance.chap01.section03.model.FoodProductJoined;
import com.ohgiraffers.inheritance.chap01.section03.model.ProductJoined;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

/*
 * joined 전략
 * - 부모와 자식 클래스를 각각 테이블로 나누고, 조인으로 연결
 * - 장점 : 테이블이 정규화되어 데이터 중복이 적고, 각 테이블이 독립적
 * - 단점 : 조회 시 조인이 필요하기 때문에 성능이 느려질 수 있음
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            ElectronicProductJoined electronicProduct = new ElectronicProductJoined("laptop", 999.99, "TechBrand", 50, 24, "65w");
            em.persist(electronicProduct);

            ClothingProductJoined clothingProduct = new ClothingProductJoined("T-shirt", 19.99, "fashionBrand", 100, "M", "cotton", "Blue");
            em.persist(clothingProduct);

            FoodProductJoined foodProduct = new FoodProductJoined("milk", 2.99, "foodBrand", 200, LocalDate.now().plusDays(7), true, "Refrigerator");
            em.persist(foodProduct);

            em.flush();
            em.clear();

            System.out.println("모든 상품 조회");
            // JPA의 1차 캐싱 기준으로 쿼리를 날리겠다는 것 -> Entity에 지정한 이름으로 해야함
            // 복잡한 쿼리를 만들어야 할 때 사용함
            em.createQuery("SELECT p from ProductJoined p", ProductJoined.class)
                    .getResultList()
                    .forEach(System.out::println);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
