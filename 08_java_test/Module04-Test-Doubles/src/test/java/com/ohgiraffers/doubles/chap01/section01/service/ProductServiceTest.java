package com.ohgiraffers.doubles.chap01.section01.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * TDD의 벽 : 의존성 문제
 * 1. 문제 상황 : 강한 결합
 * - TDD는 단위 테스트를 해야 한다. 즉, ProductService를 테스트할 때는
 *   ProductService 자신의 로직만 검증해야 한다.
 * - 하지만 아래의 ProductService는 new ProductRepository()를 통해
 *   실제 ProductRepository 객체와 강하게 결합되어 있다.
 *
 * 2. 테스트가 불가능한 이유
 *   1) 고립 실패 : ProductService를 테스트하면, ProductRepository까지 함께 테스트 된다.
 *   2) DB 의존 : ProductRepository는 실제 DB에 연결된다. TDD(단위 테스트)를 실행할 때마다
 *      DB가 켜져있어야 하며, DB 연결은 매우 느리다.
 *   3) 결과 예측 불가 : repository.findById(1L)은 DB의 상태에 따라 다른 결과를 반환하거나
 *      데이터가 없으면 예외를 발생시킨다. 테스트가 외부 환경에 종속된다.
 *
 * [결론]
 * - TDD는 Red-Green-Refactor 사이클은 즉각적인 피드백이 생명이다.
 * - DB에 의존하는 테스트는 느리고, 깨지기 쉬우며, 격리되지 않아 TDD를 불가능하게 만든다.
 */
@DisplayName("productService : 고립 테스트 시도")
class ProductServiceTest {

    @Test
    @DisplayName("TDD 실패 예시 : 고립 실패로 인한 AssertionError")
    void testServiceLogic_FailWithAssertionError() {
        ProductService productService = new ProductService();
        String expectedName = "[Product] 테스트 상품";

        String actualName = productService.getProductNameWithPrefix(1L);
        Assertions.assertEquals(expectedName, actualName);
    }

}