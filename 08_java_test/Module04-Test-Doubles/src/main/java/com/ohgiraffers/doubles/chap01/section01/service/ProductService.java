package com.ohgiraffers.doubles.chap01.section01.service;

import com.ohgiraffers.doubles.chap01.section01.model.Product;
import com.ohgiraffers.doubles.chap01.section01.repository.ProductRepository;

/*
 * [문제의 원인 2: '강한 결합(Tight Coupling)']
 * - TDD로 '고립' 테스트하고 싶은 대상은 'ProductService'의 '고유 로직'이다.
 * (예: "product가 null이면 '상품 없음' 반환", "null이 아니면 '접두사' 붙이기")
 */
public class ProductService {

    /*
     * ❗️ [TDD 안티패턴의 '핵심' 지점]
     * - 'ProductService'가 'ProductRepository'의 '실제 구현체'를
     * `new` 키워드로 '직접 생성'하고 '강하게 결합'되어 있다.
     *
     * - (문제) `ProductService`를 테스트하는 '어떤' 방법으로도,
     * '실제 DB에 연결'하는 `new ProductRepository()`의 '실행'을 '막을' 방법이 없다.
     * - (결과) '서비스(Unit) 테스트'가 'DB(Integration) 테스트'로 '오염'된다.
     * '고립(Isolation)'이 '완전히' 실패했다.
     */
    private ProductRepository repository = new ProductRepository();

    /**
     * [SUT의 고유 로직]
     * 'ID로 상품을 찾아 접두사를 붙여 반환한다.'
     * '상품이 없으면 "상품 없음"을 반환한다.'
     */
    public String getProductNameWithPrefix(long id) {
        // 1. (외부 의존성) '무거운' 리포지토리를 '직접' 호출 (고립 실패)
        Product product = repository.findById(id);

        // 2. (SUT의 '고유' 로직 1) -> TDD로 '검증하고 싶은' 첫 번째 로직
        if (product == null) {
            return "상품 없음";
        }

        // 3. (SUT의 '고유' 로직 2) -> TDD로 '검증하고 싶은' 두 번째 로직
        return product.getNameWithPrefix(); // "[Product] 상품명"
    }
}