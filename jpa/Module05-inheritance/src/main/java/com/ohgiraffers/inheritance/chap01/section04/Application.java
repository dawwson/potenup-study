package com.ohgiraffers.inheritance.chap01.section04;

import com.ohgiraffers.inheritance.chap01.section04.model.ClothingProductTPC;
import com.ohgiraffers.inheritance.chap01.section04.model.ElectronicProductTPC;
import com.ohgiraffers.inheritance.chap01.section04.model.FoodProductTPC;
import com.ohgiraffers.inheritance.chap01.section04.model.ProductTPC;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

/*
 * Table_per_class 전략 설명
 * - 각 클래스를 별도의 테이블로 저장
 * - 장점 : 조인이 없으므로 조회 성능이 빠르다
 * - 단점 : 공통 속성이 중복 저장되기 때문에 저장공간에 낭비가 발생된다.
 *         공통 속성 변경시 모든 테이블 수정을 요구하게 된다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            ElectronicProductTPC electronicProduct = new ElectronicProductTPC("laptop", 999.99, "TechBrand", 50, 24, "65w");
            em.persist(electronicProduct);

            ClothingProductTPC clothingProduct = new ClothingProductTPC("T-shirt", 19.99, "fashionBrand", 100, "M", "cotton", "Blue");
            em.persist(clothingProduct);

            FoodProductTPC foodProduct = new FoodProductTPC("milk", 2.99, "foodBrand", 200, LocalDate.now().plusDays(7), true, "Refrigerator");
            em.persist(foodProduct);

            em.flush();
            em.clear();

            em.createQuery("select p from ProductTPC p", FoodProductTPC.class)
                            .getResultList().forEach(System.out::println);

            System.out.println("모든 상품 조회");
            // JPA의 1차 캐싱 기준으로 쿼리를 날리겠다는 것 -> Entity에 지정한 이름으로 해야함
            // 복잡한 쿼리를 만들어야 할 때 사용함
            em.createQuery("SELECT p from ProductTPC p", ProductTPC.class)
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
