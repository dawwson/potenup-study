package org.ohgiraffers.springdata.chap02.repository;

import org.ohgiraffers.springdata.common.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * 1. Repository 인터페이스 : 데이터 접근의 추상화
 *   - DB 작업의 상세 구현(SQL, JDBC)를 숨기고 무엇을 할지만 정의하는 명세서이다.
 *   - 해당 인터페이스는 클래스가 아닌 인터페이스로 정의하게 된다.
 *
 * 2. JpaRepository<T, ID> 상속
 *   - Spring Data Jpa가 제공하는 인터페이스를 상속받는다.
 *   - <T> 관리할 엔티티 클래스
 *   - <ID> 해당 엔티티의 @Id 필드 타입
 *
 * 3. 핵심 : 자동 구현
 *   - 개발자는 이 인터페이스 구현 클래스를 만들지 않는다.
 *   - Spring Data Jpa가 어플리케이션 시작 시점에 자동으로 구현 객체를 생성하여 스프링 Bean으로 등록한다.
 *
 * 4. 기본 기능 즉시 사용
 *   - JpaRepository를 상속받는 것만으로, 다음과 같은 핵심 메서드를 무료로 얻게 된다.
 *   - save(T) : Create, Update
 *   - findById(ID) : 단일 조회
 *   - findAll() : 전체 조회
 *   - delete(T) : 삭제
 *   - (추가) 페이징(pageable) 및 정렬(sort) 기능
 *
 * 5. Repository 어노테이션
 *   - 1. 이 인터페이스가 데이터 접근 컴포넌트임을 명시한다.
 *   - 2. Spring JPA의 스캔 대상이 되어 자동 구현을 활성화한다.
 *   - 3. JPA 예외를 스프링이 "DataAccessException"으로 자동 변환해준다.
 */
@Repository("chap02-repository")
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // ✨ 비어있어도 괜찮습니다! ✨
    // 기본적인 CRUD 메서드는 이미 JpaRepository 인터페이스에 모두 정의되어 있습니다.
    // - save(S entity): 엔티티 저장 (Insert or Update)
    // - findById(ID id): ID로 엔티티 조회 (Optional<T> 반환)
    // - findAll(): 모든 엔티티 조회 (List<T> 반환)
    // - deleteById(ID id): ID로 엔티티 삭제
    // - count(): 전체 엔티티 개수 조회
    // - existsById(ID id): 해당 ID의 엔티티 존재 여부 확인
    // ... 등등 (더 많은 메서드는 JpaRepository 문서를 참고하세요!)
}
