package com.ohgiraffers.inheritance.chap01.section02;

import com.ohgiraffers.inheritance.chap01.section02.model.ClothingProduct;
import com.ohgiraffers.inheritance.chap01.section02.model.ElectronicProduct;
import com.ohgiraffers.inheritance.chap01.section02.model.FoodProduct;
import com.ohgiraffers.inheritance.chap01.section02.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

/*
 * Single_table 전략
 * - 모든 클래스를 하나의 테이블에 저장, Dtype 컬럼으로 클래스 구분
 * - 장점 : 쿼리가 단순하고 빠름(조인 불필요)
 * - 단점 : 자식 클래스의 컬럼이 많아지면 테이블이 비대해지고, null 값이 많아질 수 있음
 *
 * DB에서 서브타입 엔티티 표현
 * - 데이터베이스에서는 모든 자식 클래스의 데이터가 하나의 테이블(products)에 저장됨
 * - dtype 컬럼을 통해 각 행이 어떤 자식 클래스에 해당하는지 구분
 * - 예: Dtype이 "Electronic"이면 ElectronicProduct
 * - 서브타입 엔티티의 속성(warrantyPeriod, size, expirationDate 등)은 해당 행에서만 값이 채워지고,
 *   다른 서브 타입의 속성은 null로 남게 된다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            ElectronicProduct electronicProduct = new ElectronicProduct("laptop", 999.99, "TechBrand", 50, 24, "65w");
            em.persist(electronicProduct);

            ClothingProduct clothingProduct = new ClothingProduct("T-shirt", 19.99, "fashionBrand", 100, "M", "cotton", "Blue");
            em.persist(clothingProduct);

            FoodProduct foodProduct = new FoodProduct("milk", 2.99, "foodBrand", 200, LocalDate.now().plusDays(7), true, "Refrigerator");
            em.persist(foodProduct);

            em.flush();
            em.clear();

            System.out.println("모든 상품 조회");
            // JPA의 1차 캐싱 기준으로 쿼리를 날리겠다는 것 -> Entity에 지정한 이름으로 해야함
            // 복잡한 쿼리를 만들어야 할 때 사용함
            em.createQuery("SELECT p from Product p", Product.class)
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
