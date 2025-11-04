package org.ohgiraffers.springdata.chap03.repository;

import org.ohgiraffers.springdata.common.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * 1. 쿼리 메서드란?
 *   - JpaRepository 인터페이스에 정해진 키워드 규칙에 맞게 메서드를 선언하는 방식이다.
 *   - 개발자가 JPQL 쿼리 문을 직접 작성하지 않아도 된다.
 *
 * 2. 동작 원리
 *   - spring data jpa가 애플리케이션 시작 시점에 메서드 이름을 파싱한다.
 *   - 메서드 이름에 포함된 키워드(findBy, and 등)을 분석하여 JPQL 쿼리를 자동으로 생성하고 실행한다.
 *
 * 3. 예시
 *   - findByName(String name)
 *   - JPQL : select p from product p where p.name = ?
 *
 * 4. 장점
 *   - 매우 간단한 조회 쿼리를 빠르고 직관적으로 작성할 수 있다.
 *   - 쿼리문을 문자열로 작성할 때 발생하는 오타 문제를 방지할 수 있다.
 *   - 메서드 이름에 오타가 있으면 앱 시작 시점에 오류를 잡아준다.
 *
 * 5. 단점
 *   - 조건이 많아지면 메서드 이름이 극도로 길어진다.
 *   - 복잡한 쿼리(예: 조인, 서브쿼리, DTO 프로젝션)에는 한계가 있다.
 */
@Repository("chap03-productRepository")
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // JpaRepository가 기본으로 제공하는 메서드
    // save(), findById(), findAll(), deleteById(), count(), existsById()......

    /*
     * 상품 이름으로 상품 목록 조회
     * - spring data jpa가 생성하는 jpql : select p from product where p.productName = ?
     */
    List<Product> findByProductName(String productName);

    /*
     * 특정 가격보다 비싼 상품 조회
     * jpql : select p from product p where p.price > ?
     */
    List<Product> findByPriceGreaterThan(int price);

    /*
     * 특정 가격보다 낮은 상품 조회
     */
    List<Product> findByPriceLessThan(int price, Sort sort);

    /*
     * 상품 이름에 특정 키워드가 포함된 상품 목록 조회
     */
    List<Product> findByProductNameContaining(String keyword);

    // 여러 상품 ID 목록에 해당하는 상품 목록 조회
    List<Product> findByProductIdIn(List<Integer> productIds);

    // 특정 가격 범위 내의 상품 목록 조회
    List<Product> findByPriceBetween(Integer priceAfter, Integer priceBefore);

    // 상품 이름으로 가격(price) 내림차순 정렬
    List<Product> findByProductNameOrderByPriceDesc(String productName);

    // and Or, IsNull, NotNull 등 다양한 조합이 가능함
}
