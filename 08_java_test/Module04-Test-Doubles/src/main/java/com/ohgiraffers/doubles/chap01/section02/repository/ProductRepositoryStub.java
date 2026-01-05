package com.ohgiraffers.doubles.chap01.section02.repository;

import com.ohgiraffers.doubles.chap01.section02.model.Product;

/*
 * [테스트 더블 1: 'Stub (스텁)'의 수동 구현]
 * - 'src/test/java' (테스트 영역)에 'IProductRepository' 인터페이스를 구현하는 '가짜' 객체를 만든다.
 *
 * - 'Stub'이란?
 * - 테스트 더블의 한 종류로, 호출에 대해 '미리 준비된(Canned) 답변'을
 *   '하드코딩'하여 반환하는 객체이다.
 * - (예: "findById(1L)이 호출되면, 무조건 '테스트 상품'을 반환해라.")
 */
public class ProductRepositoryStub implements ProductRepository {

    @Override
    public Product findById(long id) {
        System.out.println("--- [ProductRepositoryStub] 호출됨 (실제 DB 연결 X) ---");

        // [Stub의 '하드코딩된 답변']
        // TDD 시나리오: "1L 상품을 조회하면"
        if (id == 1L) {
            // "SUT의 로직(접두사 붙이기)을 검증하기에 충분한 '가짜' Product 객체를 반환한다."
            return new Product("테스트 상품");
        }

        // "그 외의 경우는 null을 반환한다."
        return null;
    }
}
