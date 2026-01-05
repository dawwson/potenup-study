package com.ohgiraffers.doubles.chap01.section02.service;

import com.ohgiraffers.doubles.chap01.section02.repository.ProductRepository;
import com.ohgiraffers.doubles.chap01.section02.repository.ProductRepositoryStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * 테스트 더블이란?
 * 테스트 더블은 진짜 객체 대신, 테스트 목적에 맞게 동작을 흉내 내는 대체 객체를 의미한다.
 * 외부 의존성을 제거해 테스트를 더 빠르고 안정적으로 만들기 위한 기법이다.
 * - 즉, 실제 객체를 대신하여 사용되는 객체를 의미하며 위험한 장면을 연기하는 스턴트 더블에서 유래된 용어이다.
 *
 * 1. 핵심 개념 : 의존성 분리
 * - TDD를 위해 ProductService 테스트는 ProductRepository의 실제 동작과 분리되어야 한다.
 * - 해결책 : 테스트 실행 시, 실제 ProductRepository 객체를 대신한다.
 * - 테스트에 필요한 행동만 흉내 내는 가짜 객체를 주입한다.
 */
@DisplayName("ProductService 고립 테스트 성공")
public class ProductServiceTest {

    @Test
    @DisplayName("TDD 성공 : 가짜 Stub을 주입하여 Service 로직만 고립 검증")
    void testServiceLogin_WithStub() {

        ProductRepository fakeRepository = new ProductRepositoryStub();

        ProductService service = new ProductService(fakeRepository);

        String actualName = service.getProductNameWithPrefix(1L);

        String expectedName = "[Product] 테스트 상품";

        Assertions.assertEquals(expectedName, actualName);
        // DB 연결 없이 빠르게 진행하고 상관 없이 service를 고립해서 테스트 진행
    }
}
