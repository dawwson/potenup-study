package org.ohgiraffers.junit.chap03.repository;

import org.ohgiraffers.junit.chap03.model.Product;
import org.springframework.stereotype.Repository;

/*
 * [수업용] 리포지토리 인터페이스
 * - `ProductService`는 이 인터페이스에 의존합니다. (DIP - 의존성 역전 원칙)
 * - TDD 시, `@MockBean` 또는 `@Mock`은 이 인터페이스를 구현하는 가짜 객체를 생성합니다.
 */

@Repository
public interface ProductRepository {

    // 📌 Mockito 테스트에서 `given(repository.findById(...))`의 대상이 되는 메서드
    Product findById(long id);

    // 📌 Mockito 테스트에서 `given(repository.save(...))`의 대상이 되는 메서드
    Product save(Product product);

    // (기타 CRUD 메서드...)
    // void deleteById(long id);
}