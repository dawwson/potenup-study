package org.ohgiraffers.springdata.chap01.section02.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import org.ohgiraffers.springdata.common.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * 1. Repository Layer (데이터 접근 계층)
 *   - 데이터베이스와의 통신을 전담하는 계층이다.
 *   - 데이터의 영속성(persistance)를 담당한다. (즉, 데이터를 영구 저장하고 조회)
 *
 * 2. EntityManager 사용
 *   - JPA의 표준 핵심 객체인 EntityManager를 직접 주입받아 사용한다.
 *   - Spring Data JPA의 자동화 기능을 사용하지 않는 순수 구현 방식
 *
 * 3. @Repository 어노테이션
 *   - 1. 이 클래스를 스프링 Bean으로 등록한다. (IoC)
 *   - 2. 예외 변환 기능을 활성화한다.
 */
@Repository
public class ProductRepository {
    private final EntityManagerFactory emf;

    public ProductRepository(EntityManagerFactory emf) {
        System.out.println("✅ productRepository 생성");
        this.emf = emf;
    }

    public List<Product> findAll() {
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("[repository] DB에서 모든 상품 조회 시도...");
            List<Product> products = em.createQuery("select p from Product p", Product.class).getResultList();
            System.out.println("[repository] 조회된 상품 수 : " + products.size());

            return products;
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            em.close();
            System.out.println("[repository] EntityManager closed");
        }
        return null;
    }

    public Product findById(Integer productId) {
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("[repository] 특정 상품 조회 시도... productId = " + productId);
            Product product = em.find(Product.class, productId);
            System.out.println("[repository] 조회된 상품 : " + product);

            return product;
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            em.close();
            System.out.println("[repository] EntityManager closed");
        }
        return null;
    }
}
