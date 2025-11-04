package org.ohgiraffers.springdata.chap04.model;

/*
 * 1. 프로젝션이란
 *   - 엔티티의 전체 필드가 아닌 일부 특정 필드만 선택하여 조회하는 기술이다.
 *
 * 2. 인터페이스 기반 프로젝션
 *   - 조회할 필드들의 getter 메서드의 시그니처만 포함하는 인터페이스를 정의하는 방식이다.
 *
 * 3. 작성 규칙
 *   1) 인터페이스로 선언한다.
 *   2) 조회하려는 엔티티의 필드명과 정확하게 일치하는 getter 메서드를 정의한다.
 *
 * 4. 사법
 *   - 이 인터페이스를 JpaRepository 쿼리 메서드의 반환 타입으로 사용한다.
 *
 * 5. 장점
 *   - SELECT * 이 아닌 SELECT id, name 처럼 최적화된 쿼리가 실행된다.
 *   - 전체 엔티티가 아닌 가벼운 DTO를 반환받아, 네트워크 메모리 CPU 성능을 향상시킨다.
 */
public interface ProductSummary {
    Integer getProductId();
    String getProductName();
}
