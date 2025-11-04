package org.ohgiraffers.springdata.chap04.repository;

import org.ohgiraffers.springdata.chap04.model.ProductDTO;
import org.ohgiraffers.springdata.chap04.model.ProductSummary;
import org.ohgiraffers.springdata.common.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * 인터페이스 방식 : 코드가 간결하다. 조회한 데이터를 단순 읽기 전용으로 화면에 전달할 때 가장 좋다.
 * 동작 방식 : 인터페이스가 getter 이름 확인 후 프로젝션 쿼리 생성 -> DB 결과 조회 -> ProductSummary(인터페이스 생성) -> 값 주입
 * DTO 방식 : 코드는 조금 더 길지만 실제 객체가 생성된다.
 * 따라서 조회한 데이터에 추가적인 가공 로직을 포함시키거나, 계층 간의 명확한 데이터 구조를 전달하고 싶을 때 더 유연하고 강력하다.
 */
@Repository("chap04-productRepository")
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // JPQL 직접 작성
    // 특정 가격보다 낮은 상품 목록을 가격 오름차순으로 조회
    @Query("SELECT p from  Product p where p.price < :maxPrice ORDER BY p.price ASC")
    List<Product> findProductsBelowPriceSorted(@Param("maxPrice") Integer maxPrice, @Param("minPrice") Integer minPrice);
    // 가격순으로 정렬된 제품 찾기

    // JPQL + 위치 기반 파라미터 바인딩(권장❌)
    // 상품 이름에 특정 키워드가 포함된 상품 목록 조회
    @Query("SELECT p FROM Product p WHERE p.productName like %?1%")
    List<Product> findByProductNameKeyword(String keyword);
    // 위치 기반 (?1, ?2)는 파라미터 순서 변경 시 오류 발생 가능성이 있어 이름 기반을 권장함

    // JPQL + DTO 프로젝션
    // 특정 가격 이상인 상품들의 ID와 이름만 조회하여 ProductDTO 객체로 반환
    @Query("SELECT new org.ohgiraffers.springdata.chap04.model.ProductDTO(p.productId, p.productName, p.price) " +
            "FROM Product p WHERE p.price >= :minPrice")
    List<ProductDTO> findProductDTOsAbovePrice(@Param("minPrice") Integer minPrice);

    // Native SQL 사용하기
    // 상품 테이블에서 상품명과 가격만 조회하기
    @Query(value = "SELECT product_name, price FROM tbl_product WHERE price > ?1", nativeQuery = true)
    List<Object[]> findProductNameAndPriceNative(Integer minPrice);

    // Native SQL + 인터페이스 기반 프로젝션
    @Query(value = "SELECT product_id AS productId, product_name AS productName " +
                   "FROM tbl_product WHERE product_id IN (:ids)", nativeQuery = true)
    List<ProductSummary> findProductSummariesByIds(@Param("ids") List<Integer> ids);

    // 데이터 수정 쿼리
    // 특정 id 상품 가격을 업데이트하는 JPQL
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.price = :newPrice WHERE p.productId = :id")
    int updateProductPrice(@Param("id") Integer id, @Param("newPrice") Integer newPrice);
    /*
     * modifying 어노테이션은 해당 쿼리가 데이터 변경 작업임을 알리고,
     * 영속성 컨텍스트와의 동기화 등 추가 작업을 처리하도록 한다.
     * 반환 타입은 보통 int 또는 void이다.
     */
}
